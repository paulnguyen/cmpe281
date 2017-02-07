package jpa.mapping;

public enum AccountState {

	Activated, Blocked;
	
}


/*

AccountState.java

package com.exptutorials.enumerated;

public enum AccountState {

	Activated, Blocked;
	
}

// In case the database column is a INT type, we use EnumType.ORDINAL

@Enumerated(EnumType.ORDINAL)
@Column(name = "state")
private AccountState state;

// If the database column is a VARCHAR type, we will use EnumType.STRING

@Enumerated(EnumType.STRING)
@Column(name = "state")
private AccountState state;


*/