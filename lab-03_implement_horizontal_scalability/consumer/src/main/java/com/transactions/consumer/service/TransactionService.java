package com.transactions.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transactions.consumer.dto.TransactionDto;
import com.transactions.consumer.model.Transaction;
import com.transactions.consumer.repository.TransactionRepository;

@Service
public class TransactionService  implements ITransactionService{

	@Autowired
	private TransactionRepository saleRepository;
	
	/**
	 * Persists a sale
	 * @param saleDto
	 * @return
	 */
	public Transaction save(TransactionDto saleDto) {
		Transaction sale = Transaction.from(saleDto);
		return this.saleRepository.save(sale);
	}
	
}
