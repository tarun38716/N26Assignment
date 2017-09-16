package com.n26.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.dto.Stats;
import com.n26.dto.Transaction;
import com.n26.exception.TransactionException;
import com.n26.service.TransactionService;

import io.swagger.annotations.ApiOperation;

/**
 * @author tarun
 * RestController to Recieve all the requests
 */
@RestController
@RequestMapping(value = "/n26app")
public class TransactionController {

	@Autowired
	@Qualifier("TransactionService")
	private TransactionService transactionService;

	@PostMapping("/transactions")
	@ApiOperation(notes = "Add a Transaction which is not older than 60secs", value = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postTransaction(@RequestBody(required = true) final Transaction transaction) {
		boolean result = false;
		try {
			if(transaction.getAmount() <=0 || transaction.getTimestamp()<=0){
				return new ResponseEntity<String>(BAD_REQUEST);
			}
			result = transactionService.postTransaction(transaction);
			if(result)
				return new ResponseEntity<String>(CREATED);
			else
				return new ResponseEntity<String>(NO_CONTENT);
		} catch (TransactionException e) {
			e.getExcep().printStackTrace();
			return new ResponseEntity<String>(e.getErrorMessage(),INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/transactions")
	@ApiOperation(notes = "Return a List of Transactions added", value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTransactions(){
		try {
			return new ResponseEntity<List<Transaction>>(transactionService.getTransactions(),OK);
		} catch (TransactionException e) {
			e.getExcep().printStackTrace();
			return new ResponseEntity<String>(e.getErrorMessage(),INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/statistics")
	@ApiOperation(notes = "Return a Statistics of Transactions not older than 60secs", value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStatistics(){
		try {
			return new ResponseEntity<Stats>(transactionService.getStatistics(), OK);
		} catch (TransactionException e) {
			e.getExcep().printStackTrace();
			return new ResponseEntity<String>(e.getErrorMessage(),INTERNAL_SERVER_ERROR);
		}
	}
}
