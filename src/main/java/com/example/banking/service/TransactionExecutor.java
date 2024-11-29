package com.example.banking.service;

import com.example.banking.service.dto.TransactionRequestDto;
import com.example.banking.service.dto.TransactionType;
import com.example.banking.service.transaction.observer.TransactionObserver;

import java.util.ArrayList;
import java.util.List;

public interface TransactionExecutor {

	List<TransactionObserver> observers = new ArrayList<>();

	<T extends TransactionRequestDto> Boolean execute(TransactionType type, T request);

	default void addObserver(TransactionObserver observer) {
		observers.add(observer);
	}
}