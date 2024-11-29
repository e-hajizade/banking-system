package com.example.banking.service.impl;

import com.example.banking.service.ConsoleInterface;
import com.example.banking.service.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

@Service
public class ConsoleInterfaceImpl implements ConsoleInterface {

	private final BankService bankService;
	private final ExecutorService executorService;
	private final List<Callable<Boolean>> tasks = new ArrayList<>();

	public ConsoleInterfaceImpl(BankService bankService) {
		this.bankService = bankService;
		this.executorService = Executors.newFixedThreadPool(10);
	}

	@Override
	public void start() {
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		displayMenu();
		while (running) {

			int choice = scanner.nextInt();

			switch (choice) {
				case 1:
					viewAccount(scanner);
					break;
				case 2:
					createAccount(scanner);
					break;
				case 3:
					handleDeposit(scanner);
					break;
				case 4:
					handleWithdraw(scanner);
					break;
				case 5:
					handleTransfer(scanner);
					break;
				case 6: {
					executeTasks();
					break;
				}
				case 7: {
					running = false;
					break;
				}
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}

		executorService.shutdown();
	}

	@Override
	public void displayMenu() {
		System.out.println("\n1. View Account Details");
		System.out.println("2. Create Account");
		System.out.println("3. Deposit");
		System.out.println("4. Withdraw");
		System.out.println("5. Transfer");
		System.out.println("6. execute");
		System.out.println("7. exit\n");
	}

	private void viewAccount(Scanner scanner) {
		scanner.nextLine();
		System.out.print("Enter Account ID: ");
		long accountId = scanner.nextLong();
		try {
			BankAccountDto account = bankService.getAccount(accountId);
			System.out.print("Account ID: " + account.getAccountId());
			System.out.print(" , Holder Name: " + account.getHolderName());
			System.out.println(" , Balance: " + account.getBalance());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void createAccount(Scanner scanner) {
		scanner.nextLine();
		System.out.print("Enter Holder Name: \n");
		String holderName = scanner.nextLine();
		System.out.print("Enter Initial Balance: ");
		double balance = scanner.nextDouble();
		scanner.nextLine();
		BankAccountDto newAccount = new BankAccountDto(holderName, balance);

		try {
			BankAccountDto createdAccount = bankService.create(newAccount);
			System.out.println("Account Created Successfully! Account ID: " + createdAccount.getAccountId());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void handleDeposit(Scanner scanner) {
		System.out.print("Enter Account ID: ");
		long accountId = scanner.nextLong();
		scanner.nextLine();
		System.out.print("Enter Deposit Amount: ");
		double amount = scanner.nextDouble();

		TransactionRequestDto transactionDto = new AccountTransactionRequestDto(amount, accountId);

		try {
			tasks.add(() -> bankService.doTransaction(TransactionType.DEPOSIT, transactionDto));
//			executorService.submit(()->bankService.doTransaction(TransactionType.DEPOSIT, transactionDto));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void handleWithdraw(Scanner scanner) {
		System.out.print("Enter Account ID: ");
		long accountId = scanner.nextLong();
		System.out.print("Enter Withdrawal Amount: ");
		double amount = scanner.nextDouble();

		TransactionRequestDto transactionDto = new AccountTransactionRequestDto(amount, accountId);

		try {
			tasks.add(() -> bankService.doTransaction(TransactionType.WITHDRAW, transactionDto));

//			executorService.submit(()->bankService.doTransaction(TransactionType.WITHDRAW, transactionDto));

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void handleTransfer(Scanner scanner) {
		System.out.print("Enter Source Account ID: ");
		long fromAccountId = scanner.nextLong();
		System.out.print("Enter Destination Account ID: ");
		long toAccountId = scanner.nextLong();
		System.out.print("Enter Transfer Amount: ");
		double amount = scanner.nextDouble();

		TransactionRequestDto transactionDto = new TransferTransactionDto(amount, fromAccountId, toAccountId);

		try {
			tasks.add(() -> bankService.doTransaction(TransactionType.TRANSFER, transactionDto));

//			executorService.submit(()->bankService.doTransaction(TransactionType.TRANSFER, transactionDto));

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void executeTasks() {
		try {
			List<Future<Boolean>> futures = executorService.invokeAll(tasks);
			for (int i = 0; i < futures.size(); i++) {
				try {
					System.out.println("transaction " + (i + 1) + ": " + (futures.get(i).get() ? "done" : ""));
				} catch (ExecutionException exception) {
					System.out.println("Exception in transaction " + (i + 1) + ": " + exception.getCause().getMessage());
				}

			}
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}

		tasks.clear();
	}
}
