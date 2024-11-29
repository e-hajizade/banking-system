package com.example.banking.service.transaction.strategy;

import com.example.banking.entity.BankAccount;
import com.example.banking.repository.BankAccountRepository;
import com.example.banking.service.dto.TransactionType;
import com.example.banking.service.dto.TransferTransactionDto;
import com.example.banking.service.transaction.factory.TransactionStrategyFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TransferTransaction implements TransactionStrategy<TransferTransactionDto> {

	private final BankAccountRepository accountRepository;

	public TransferTransaction(BankAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public void execute(TransferTransactionDto transactionDto) {

		BankAccount fromAccount = accountRepository.findAccountForUpdate(transactionDto.getFromAccountId())
				.orElseThrow(() -> new RuntimeException("Source account not found"));
		BankAccount toAccount = accountRepository.findAccountForUpdate(transactionDto.getToAccountId())
				.orElseThrow(() -> new RuntimeException("Destination account not found"));

		validate(fromAccount, toAccount, transactionDto);

		fromAccount.setBalance(fromAccount.getBalance() - transactionDto.getAmount());
		toAccount.setBalance(toAccount.getBalance() + transactionDto.getAmount());

		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);
	}

	@PostConstruct
	@Override
	public void register() {
		TransactionStrategyFactory.addTransactionStrategy(TransactionType.TRANSFER, this);
	}

	private void validate(BankAccount fromAccount, BankAccount toAccount, TransferTransactionDto transactionDto) {

		if (fromAccount.getBalance() < transactionDto.getAmount()) {
			throw new RuntimeException("Insufficient funds");
		}

		if (fromAccount.getAccountId().equals(toAccount.getAccountId()))
			throw new RuntimeException("source and destination account are the same");

	}
}
