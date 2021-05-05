package com.revature.main;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class MainTest {
	private static Logger log = LogManager.getRootLogger();
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void test() {
		boolean expected = true;
		assertTrue("asserts is true", expected);
	}

}
