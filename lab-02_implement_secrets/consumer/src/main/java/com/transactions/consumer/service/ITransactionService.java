package com.transactions.consumer.service;

import com.transactions.consumer.dto.TransactionDto;
import com.transactions.consumer.model.Transaction;

public interface ITransactionService {

	Transaction save(TransactionDto saleDto);
}
