package com.claytablet.module;

import com.claytablet.service.event.ProducerReceiver;
import com.claytablet.service.event.drupal.ProducerReceiverDrupal;

/**
 * Copyright 2007 Clay Tablet Technologies Inc.
 * 
 * <p>
 * 
 * @author <a href="mailto:drapin@clay-tablet.com">Dave Rapin</a>
 * 
 * <p>
 * Drupal module for Guice configuration. Extends the SQSS3Module and overrides
 * the default receiver binding for a drupal implementation.
 */
public class DrupalSQSS3Module extends SQSS3Module {

	protected void configure() {

		super.configure();

		// override the default receiver binding
		bind(ProducerReceiver.class).to(ProducerReceiverDrupal.class);

	}
}
