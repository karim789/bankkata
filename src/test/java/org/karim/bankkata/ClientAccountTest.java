package org.karim.bankkata;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bankkata.model.Account;
import bankkata.model.Client;
import bankkata.model.Operation;
import bankkata.model.OperationTypeEnum;
import bankkata.service.AccountService;

public class ClientAccountTest {

	private AccountService accountService;
	
	@BeforeEach
	public void setup() {
		
		Client clientToto = Client.builder().name("toto").build();
		
		LocalDateTime now = LocalDateTime.now();
		
		Operation deposit = Operation.builder()
		  .operationType(OperationTypeEnum.DEPOSIT)
		  .date(now)
		  .amount(100)
		  .client(clientToto)
		  .build();
		
		List<Operation> operations = new ArrayList<>();
		operations.add(deposit);
		
		Account totoAccount = Account.builder()
				.name("compte courant")
				.balanceDate(now)
				.balance(100)
				.operations(operations)
				.build();
		
		accountService = new AccountService();
		accountService.addAccount(clientToto, totoAccount);
		
	}
	
	@Test
	public void saveMoneyTest() {
		
		Client clientToto = Client.builder().name("toto").build();
		accountService.deposit(clientToto, 15);
		
		Assertions.assertEquals(115, accountService.getBalance(clientToto));
		
	}
	
	@Test
	public void retrieveSomeOrAllMoneyTest() {
		
		Client clientToto = Client.builder().name("toto").build();
		accountService.withDraw(clientToto, 15);
		
		Assertions.assertEquals(85, accountService.getBalance(clientToto));
		
		Assertions.assertEquals(85, accountService.withDrawAll(clientToto));
		
		Assertions.assertEquals(0, accountService.getBalance(clientToto));
		
	}

	@Test
	public void seeHistoryTest() {
		
		Client clientToto = Client.builder().name("toto").build();
		accountService.withDraw(clientToto, 15);
		accountService.deposit(clientToto, 5);
		accountService.deposit(clientToto, 4);
		accountService.withDrawAll(clientToto);
		
		Assertions.assertEquals(5, accountService.getOperations(clientToto).size());
		
		accountService.printOperations(clientToto);
		
	}
	
}
