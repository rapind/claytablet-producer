package com.claytablet.module;

import com.claytablet.service.event.ProducerReceiver;
import com.claytablet.service.event.mock.ProducerReceiverMock;

/**
 * Copyright 2007 Clay Tablet Technologies Inc.
 * 
 * <p>
 * 
 * @author <a href="mailto:drapin@clay-tablet.com">Dave Rapin</a>
 * 
 * <p>
 * Mock module for Guice configuration. Extends the SQSS3Module and overrides
 * the default receiver binding for a mock implementation.
 */
public class MockSQSS3Module extends SQSS3Module {

	protected void configure() {

		super.configure();

		// override the default receiver binding
		bind(ProducerReceiver.class).to(ProducerReceiverMock.class);

	}
}
