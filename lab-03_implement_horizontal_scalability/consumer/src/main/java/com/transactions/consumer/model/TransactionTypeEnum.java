package com.transactions.consumer.model;

import java.util.HashMap;
import java.util.Map;

public enum TransactionTypeEnum {
	
	SALE(1),RETURN(2),TRANSFER(3),PURCHASE(4);
	
	/*
	 * Created a Map to get the Enum by it's id.
	 * This is a a simple case, but in a more complex case the MAp is the ideal data structure
	 * 
	 */
	private static final Map<Integer, TransactionTypeEnum> BY_ID = new HashMap<>();
	static {
	        for (TransactionTypeEnum e : values()) {
	            BY_ID.put(e.id, e);    
	        }
	    }

	public final Integer id;
	
	
	
	private TransactionTypeEnum(int id) {
		this.id = id;
	}
	
	public static TransactionTypeEnum byId(Integer id) {
		return BY_ID.get(id);
	}
	
}
