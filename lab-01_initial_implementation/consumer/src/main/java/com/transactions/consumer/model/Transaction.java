package com.transactions.consumer.model;

import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.transactions.consumer.dto.TransactionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document
public class Transaction {
	@Id
	private String id;
	private String transactionId;
	private String description;
	private Float amount;
	private String transactionTimestamp;
	private String transactionType;

	public static Transaction from(TransactionDto e) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

		return  Transaction.builder()
		  .amount(e.getAmount())
		  .description(e.getDescription())
		  .transactionId(e.getTransactionId())
		  .transactionTimestamp(dtf.format(e.getTransactionTimestamp().toLocalDateTime()))
		  .transactionType(TransactionTypeEnum.byId(e.getTransactionType()).toString()).build();
		  
	}
}
