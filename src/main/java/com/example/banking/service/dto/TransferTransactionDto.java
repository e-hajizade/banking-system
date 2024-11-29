package com.example.banking.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferTransactionDto extends TransactionRequestDto {

	private Long fromAccountId;
	private Long toAccountId;

	public TransferTransactionDto(Double amount, Long fromAccountId, Long toAccountId) {
		super(amount);
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
	}

	@Override
	public String toString() {
		return "{ amount= " + this.getAmount() +
				"  fromAccountId= " + getFromAccountId() +
				"  toAccountId= " + getToAccountId() +
				" }";
	}
}
