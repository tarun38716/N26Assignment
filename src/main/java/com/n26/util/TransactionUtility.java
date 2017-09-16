package com.n26.util;

import java.time.Instant;

/**
 * @author tarun
 * Helper Class for Transaction Rest API
 */
public class TransactionUtility {

	public static boolean check60Sec(long timestamp){
		long currentTimestamp = Instant.now().toEpochMilli();
		long timeDiff = currentTimestamp-timestamp;
		if(timeDiff>=0 && timeDiff<=60000)
			return true;
		return false;
	}
}
