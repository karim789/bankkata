package bankkata.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OperationTypeEnum {

	DEPOSIT("deposit"),
	WITHDRAWAL("withdrawal");
	
	private final String name;
	
	@Override
	public String toString() {
		return name;
	}
	
}
