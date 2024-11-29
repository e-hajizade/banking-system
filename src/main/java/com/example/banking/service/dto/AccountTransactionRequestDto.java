package com.example.banking.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountTransactionRequestDto extends TransactionRequestDto {

	private Long accountId;

	public AccountTransactionRequestDto(Double amount, Long accountId) {
		super(amount);
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "{ amount= " + this.getAmount() +
				" accountId= " + getAccountId() +
				" }";
	}
}
