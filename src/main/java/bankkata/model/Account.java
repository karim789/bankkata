package bankkata.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {

	private String name;
	private LocalDateTime balanceDate;
	private double balance;
	private List<Operation> operations;
	
}
