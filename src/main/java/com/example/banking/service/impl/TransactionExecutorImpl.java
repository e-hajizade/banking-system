package com.example.banking.service.impl;

import com.example.banking.service.TransactionExecutor;
import com.example.banking.service.dto.TransactionRequestDto;
import com.example.banking.service.dto.TransactionType;
import com.example.banking.service.transaction.factory.TransactionStrategyFactory;
import com.example.banking.service.transaction.observer.TransactionLogger;
import com.example.banking.service.transaction.observer.TransactionObserver;
import com.example.banking.service.transaction.strategy.TransactionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionExecutorImpl implements TransactionExecutor {

	private final TransactionStrategyFactory transactionStrategyFactory;

	public TransactionExecutorImpl(TransactionLogger logger, TransactionStrategyFactory transactionStrategyFactory) {
		this.transactionStrategyFactory = transactionStrategyFactory;
		addObserver(logger);
	}

	@Override
	@Transactional
	public <T extends TransactionRequestDto> Boolean execute(TransactionType type, T request) {
		TransactionStrategy<T> strategy = transactionStrategyFactory.getTransactionStrategy(type);
		try {
			strategy.execute(request);
			notifyObservers(type, request, "done");
			return true;
		} catch (Exception e) {
			notifyObservers(type, request, e.getMessage());
			throw e;
		}
	}

	private void notifyObservers(TransactionType type, TransactionRequestDto transactionDto, String status) {
		for (TransactionObserver observer : observers) {
			observer.onTransaction(type, transactionDto, status);
		}
	}

}
