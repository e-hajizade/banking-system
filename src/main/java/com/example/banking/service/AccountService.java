package com.example.banking.service;

import com.example.banking.service.dto.BankAccountDto;

import javax.validation.Valid;

public interface AccountService {

	BankAccountDto getAccount(Long id);

	BankAccountDto create(@Valid BankAccountDto bankAccountDto);
}
