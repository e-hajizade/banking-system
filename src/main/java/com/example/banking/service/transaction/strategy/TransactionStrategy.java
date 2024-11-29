package com.example.banking.service.transaction.strategy;

import com.example.banking.service.dto.TransactionRequestDto;

import javax.validation.Valid;

public interface TransactionStrategy<T extends TransactionRequestDto> {

	void execute(@Valid T transactionRequest);

	void register();
}
