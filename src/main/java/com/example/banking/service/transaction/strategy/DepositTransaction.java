package com.example.banking.service.transaction.strategy;

import com.example.banking.entity.BankAccount;
import com.example.banking.repository.BankAccountRepository;
import com.example.banking.service.dto.AccountTransactionRequestDto;
import com.example.banking.service.dto.TransactionType;
import com.example.banking.service.transaction.factory.TransactionStrategyFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DepositTransaction implements TransactionStrategy<AccountTransactionRequestDto> {

	private final BankAccountRepository accountRepository;

	public DepositTransaction(BankAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public void execute(AccountTransactionRequestDto transactionDto) {
		BankAccount bankAccount = accountRepository.findAccountForUpdate(transactionDto.getAccountId()).orElseThrow(() -> new RuntimeException("account not found"));
		bankAccount.setBalance(bankAccount.getBalance() + transactionDto.getAmount());
		accountRepository.save(bankAccount);
	}

	@PostConstruct
	@Override
	public void register() {
		TransactionStrategyFactory.addTransactionStrategy(TransactionType.DEPOSIT, this);
	}
}
