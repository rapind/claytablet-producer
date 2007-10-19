package com.claytablet.module;

import com.claytablet.service.event.AccountManager;
import com.claytablet.service.event.ProducerReceiver;
import com.claytablet.service.event.drupal.ProducerReceiverDrupal;
import com.claytablet.service.event.impl.AccountManagerImpl;

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

		// specify account manager binding
		bind(AccountManager.class).to(AccountManagerImpl.class);

		// override the default receiver binding
		bind(ProducerReceiver.class).to(ProducerReceiverDrupal.class);

	}
}
