package com.example.banking.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "BANK_ACCOUNT")
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;

	@Column(nullable = false, length = 20)
	private String holderName;

	@Column(nullable = false)
	private Double balance;



}
