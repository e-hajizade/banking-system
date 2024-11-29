package com.example.banking.service.dto;

import com.example.banking.entity.validator.MinBalance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class BankAccountDto {

	private Long accountId;

	@Size(max=10,message = "Holder name must have less than maximum 10 characters")
	@NotBlank(message = "Holder name cannot be blank")
	@NotNull(message = "Holder name cannot be null")
	private String holderName;

	@NotNull(message = "Balance cannot be null")
	@MinBalance
	private Double balance;

	public BankAccountDto(String holderName, Double balance) {
		this.holderName = holderName;
		this.balance = balance;
	}
}
