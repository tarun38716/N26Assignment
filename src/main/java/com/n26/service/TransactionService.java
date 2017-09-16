package com.n26.service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.n26.dao.TransactionDao;
import com.n26.dto.Stats;
import com.n26.dto.Transaction;
import com.n26.exception.TransactionException;
import com.n26.util.TransactionUtility;

/**
 * @author tarun
 * Business Logic for the Rest APIs
 */
@Service("TransactionService")
public class TransactionService {

	@Autowired
	@Qualifier("TransactionDao")
	private TransactionDao transactionDao;

	public boolean postTransaction(final Transaction transaction) throws TransactionException {
		try{
			if(TransactionUtility.check60Sec(transaction.getTimestamp())){
				transactionDao.addTransaction(transaction);
				return true;
			}
			return false;
		} catch (Exception e){
			throw new TransactionException(e, "Internal Error : Please try again later");
		}

	}

	public List<Transaction> getTransactions() throws TransactionException{
		try{
			return transactionDao.getTransactions();
		} catch (Exception e){
			throw new TransactionException(e, "Internal Error : Please try again later");
		}

	}

	public Stats getStatistics() throws TransactionException {
		try{
			List<Transaction> transactions = transactionDao.getTransactions();
			DoubleSummaryStatistics result = transactions.stream().filter(t-> TransactionUtility.check60Sec(t.getTimestamp())).collect(Collectors.summarizingDouble(Transaction :: getAmount));
			return formatResult(result);
		} catch (Exception e){
			throw new TransactionException(e, "Internal Error : Please try again later");
		}
		
	}

	private Stats formatResult(DoubleSummaryStatistics result){
		final Stats statistics = new Stats();
		statistics.setCount(result.getCount());
		statistics.setAvg(result.getAverage());
		statistics.setMax(result.getMax());
		statistics.setMin(result.getMin());
		statistics.setSum(result.getSum());
		return statistics;
	}
}
