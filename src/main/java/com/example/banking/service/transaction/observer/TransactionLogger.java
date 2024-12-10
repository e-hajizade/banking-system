package com.example.banking.service.transaction.observer;

import com.example.banking.service.dto.TransactionRequestDto;
import com.example.banking.service.dto.TransactionType;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class TransactionLogger implements TransactionObserver {

	private static final String LOG_FILE = "transaction_log.txt";

	@Override
	public void onTransaction(TransactionType type, TransactionRequestDto transactionDto, String status) {
		try (FileWriter fileWriter = new FileWriter(LOG_FILE, true);
			 PrintWriter printWriter = new PrintWriter(fileWriter)) {
			printWriter.printf("Transaction: %s | details: %s | status: %s", type, transactionDto, status);
			printWriter.println();
		} catch (IOException e) {
			System.err.println("Error logging transaction: " + e.getMessage());
		}
	}

}