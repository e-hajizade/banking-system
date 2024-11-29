package com.example.banking.service.transaction.factory;

import com.example.banking.service.dto.TransactionRequestDto;
import com.example.banking.service.dto.TransactionType;
import com.example.banking.service.transaction.strategy.TransactionStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionStrategyFactory {

	private static final Map<TransactionType, TransactionStrategy<? extends TransactionRequestDto>> strategies = new HashMap<>();

	public static void addTransactionStrategy(TransactionType type, TransactionStrategy<?> strategy) {
		strategies.putIfAbsent(type, strategy);
	}

	@SuppressWarnings("unchecked")
	public <T extends TransactionRequestDto> TransactionStrategy<T> getTransactionStrategy(TransactionType transactionType) {
		TransactionStrategy<? extends TransactionRequestDto> transactionStrategy = strategies.get(transactionType);
		if (transactionStrategy == null) {
			throw new IllegalArgumentException("Unsupported transaction type");
		}
		return (TransactionStrategy<T>) transactionStrategy;
	}
}
