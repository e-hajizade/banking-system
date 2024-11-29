package com.example.banking.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
public class TransactionRequestDto {

	@Positive(message = "amount must greater than 0")
	private Double amount;

}
