package com.example.banking.mapper;

import com.example.banking.entity.BankAccount;
import com.example.banking.service.dto.BankAccountDto;

public class BankAccountMapper {

	public static BankAccount dtoToEntity(BankAccountDto bankAccountDto) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(bankAccountDto.getBalance());
		bankAccount.setHolderName(bankAccountDto.getHolderName());
		return bankAccount;
	}

	public static BankAccountDto entityToDto(BankAccount bankAccount) {
		BankAccountDto bankAccountDto = new BankAccountDto();
		bankAccountDto.setAccountId(bankAccount.getAccountId());
		bankAccountDto.setBalance(bankAccount.getBalance());
		bankAccountDto.setHolderName(bankAccount.getHolderName());
		return bankAccountDto;
	}
}
