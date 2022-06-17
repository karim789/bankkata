package bankkata.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Operation {
	
	private LocalDateTime date;
	private double amount;
	private Client client;
	private OperationTypeEnum operationType;
	
	@Override
	public String toString() {
		
		return String.format("%s : %s at time %s", operationType, getAmount(), date.toString());
	}
	
}
