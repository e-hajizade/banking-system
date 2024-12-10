package com.example.banking.service.impl;

import com.example.banking.entity.BankAccount;
import com.example.banking.mapper.BankAccountMapper;
import com.example.banking.repository.BankAccountRepository;
import com.example.banking.service.AccountService;
import com.example.banking.service.TransactionExecutor;
import com.example.banking.service.dto.BankAccountDto;
import com.example.banking.service.dto.TransactionRequestDto;
import com.example.banking.service.dto.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;





@Service
@Validated
public class BankService implements AccountService {

	private final TransactionExecutor transactionExecutor;
	private final BankAccountRepository accountRepository;

	@Autowired
	public BankService(TransactionExecutor transactionExecutor, BankAccountRepository accountRepository) {
		this.transactionExecutor = transactionExecutor;
		this.accountRepository = accountRepository;
	}

	@Override
	public BankAccountDto getAccount(Long id) {
		BankAccount bankAccount = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("account not found"));
		return BankAccountMapper.entityToDto(bankAccount);
	}

	@Override
	public BankAccountDto create(@Valid BankAccountDto bankAccountDto) {
		BankAccount savedAccount = accountRepository.save(BankAccountMapper.dtoToEntity(bankAccountDto));
		return BankAccountMapper.entityToDto(savedAccount);
	}

	public Boolean doTransaction(TransactionType type, TransactionRequestDto transactionRequestDto) {
		return transactionExecutor.execute(type, transactionRequestDto);
	}

}
