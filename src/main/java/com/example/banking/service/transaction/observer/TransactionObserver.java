package com.example.banking.service.transaction.observer;

import com.example.banking.service.dto.TransactionRequestDto;
import com.example.banking.service.dto.TransactionType;

public interface TransactionObserver {

	void onTransaction(TransactionType type, TransactionRequestDto transactionDto,String status);
}