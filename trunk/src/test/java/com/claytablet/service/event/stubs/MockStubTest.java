package com.claytablet.service.event.stubs;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;

public class MockStubTest extends TestCase {

	private final Log log = LogFactory.getLog(getClass());

	private MockStub stub;

	/**
	 * This is run before every unit test and is used to setup test variables.
	 */
	@Before
	public void setUp() {

		log.debug("SETUP: ");

		log.debug("Inject the stub.");
		stub = Guice.createInjector().getProvider(MockStub.class).get();

	}

	/**
	 * This is run after every unit test and is used to undo changes as
	 * necessary.
	 */
	@After
	public void tearDown() {

		log.debug("TEARDOWN: ");
	}

	// -------------------------------------------------------------------------
	// Tests
	// -------------------------------------------------------------------------

	/**
	 * Tests logConfig.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogConfig() throws Exception {

		stub.logConfig();
		assertTrue(true);
	}

}
