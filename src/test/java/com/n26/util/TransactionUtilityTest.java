package com.n26.util;

import static org.junit.Assert.*;

import java.time.Instant;

import org.junit.Test;

/**
 * @author tarun
 *
 */
public class TransactionUtilityTest {

	@Test
	public void check60SecValidTimeStampTest() {
		long timestamp = Instant.now().toEpochMilli();
		assertTrue(TransactionUtility.check60Sec(timestamp));
	}
	
	@Test
	public void check60SecInValidTimeStampTest() {
		long timestamp = Instant.now().toEpochMilli();
		timestamp -= 60001;
		assertFalse(TransactionUtility.check60Sec(timestamp));
	}

}
