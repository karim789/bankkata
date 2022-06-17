package bankkata.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import bankkata.model.Account;
import bankkata.model.Client;
import bankkata.model.Operation;
import bankkata.model.OperationTypeEnum;
import lombok.NonNull;

public class AccountService {

	private Map<Client, Account> accounts = new HashMap<>();
	
	private Comparator<Operation> reverseOrderSort = Collections.reverseOrder(new Comparator<Operation>() {
		@Override
		public int compare(Operation o1, Operation o2) {
			return o1.getDate().compareTo(o2.getDate());
		}
	});
	
	public Account getClientAccount(@NonNull Client client) {
		return Optional.ofNullable(accounts.get(client)).orElseThrow(() -> new BusinessException("account doesn't exist")); 
	};
	
	public Account addAccount(@NonNull Client client, @NonNull Account account) {
		return accounts.put(client, account); 
	};
	
	public void withDraw(@NonNull Client client, double amount) {
		if(amount <= 0 ) {
			throw new BusinessException("withdrawed amount must be superior than 0");
		}
		
		Account clientAccount = getClientAccount(client);
		LocalDateTime operationDateTime = LocalDateTime.now();		
		double nextBalance = clientAccount.getBalance() - amount;
		if(nextBalance < 0) {
			throw new BusinessException("account balance can't be negative");
		} else {
			clientAccount.setBalance(nextBalance);
			clientAccount.setBalanceDate(operationDateTime);
		}
		
		Operation withDrawal = Operation.builder()
				  .operationType(OperationTypeEnum.WITHDRAWAL)
				  .date(operationDateTime)
				  .amount(-amount)
				  .client(client)
				  .build();
		
		List<Operation> operations = clientAccount.getOperations();
		operations.add(withDrawal);
	}
	
	public double withDrawAll(@NonNull Client client) {
	
		double currentBalance = getBalance(client);
		
		if(currentBalance <= 0 ) {
			throw new BusinessException("there is no money left");
		}
		
		withDraw(client, currentBalance);
		
		return currentBalance;
	}

	public double getBalance(Client client) {
		Account clientAccount = getClientAccount(client);
	
		return clientAccount.getBalance();
	}

	public void deposit(@NonNull Client client, double amount) {
		if(amount <= 0 ) {
			throw new BusinessException("deposited amount must be superior than 0");
		}
		
		LocalDateTime operationDateTime = LocalDateTime.now();		

		
		Account clientAccount = getClientAccount(client); 
		clientAccount.setBalance(clientAccount.getBalance() + amount);
		clientAccount.setBalanceDate(operationDateTime);
		
		Operation deposit = Operation.builder()
				  .operationType(OperationTypeEnum.DEPOSIT)
				  .date(LocalDateTime.now())
				  .amount(amount)
				  .client(client)
				  .build();
		
		List<Operation> operations = clientAccount.getOperations();
		operations.add(deposit);
	}
	
	public List<Operation> getOperations(@NonNull Client client) {
		Account clientAccount = getClientAccount(client);
		return clientAccount.getOperations();
	}
	
	public void printOperations(@NonNull Client client) {
		
		System.out.println(String.format("balance %s", getBalance(client)));
		System.out.println();
		
		ArrayList<Operation> reversedList = new ArrayList<>(getOperations(client));
		reversedList.forEach(System.out::println);
	}
	
	
}
