package com.example.banking.service;

import com.example.banking.service.dto.BankAccountDto;

public interface AccountService {

	BankAccountDto getAccount(Long id);

	BankAccountDto create(BankAccountDto bankAccountDto);
}
