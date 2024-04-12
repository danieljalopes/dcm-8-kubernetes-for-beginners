package com.transactions.consumer.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transactions.consumer.dto.TransactionDto;
import com.transactions.consumer.model.Transaction;
import com.transactions.consumer.service.ITransactionService;



@RequestMapping("/transactions")
@RestController
public class TransactionController {

	Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	private ITransactionService saleService;
	
	@PostMapping
	public String postData(@RequestBody TransactionDto saleDto) {
	
		  
		  logger.info("Received transaction id: {}, amount: {}", saleDto.getTransactionId(), saleDto.getAmount());
		Transaction sale = null;
		  try {
			  sale = saleService.save(saleDto);
			  
			  logger.info("Transaction id: {} Persisted with id: {}", saleDto.getTransactionId(), sale.getId());
		  }catch(Exception e) {
			  logger.error(e.getMessage());
			  return String.format("ERROR - Transaction id %s", saleDto.getTransactionId());
		  }

		  return String.format("SUCCESS - Transaction id: %s Persisted with id: %s", saleDto.getTransactionId(), sale.getId());
	}
}
