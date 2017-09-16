package com.n26.configuration;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.n26.dao.TransactionDao;
import com.n26.dto.Transaction;
import com.n26.util.TransactionUtility;

/**
 * @author tarun
 * This is Start Event. Scheduler that runs after every 60 seconds to clear unnessecary transactions which are older then 60 seconds
 *
 */
@Component
public class CleaningScheduler implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private TransactionDao transactionDao;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		Runnable cleaner = new Runnable() {
			public void run() {
				try{
					List<Transaction> transactions = transactionDao.getTransactions().stream().filter(t-> TransactionUtility.check60Sec(t.getTimestamp())).collect(Collectors.toList());
					transactionDao.setTransactions(transactions);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		};
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(cleaner, 60, 60, TimeUnit.SECONDS);
	}
}
