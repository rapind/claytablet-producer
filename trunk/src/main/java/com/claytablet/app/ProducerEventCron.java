package com.claytablet.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.module.SQSS3Module;
import com.claytablet.service.event.EventListener;
import com.claytablet.service.event.impl.ProducerEventListenerImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Copyright 2007 Clay Tablet Technologies Inc.
 * 
 * <p>
 * 
 * @author <a href="mailto:drapin@clay-tablet.com">Dave Rapin</a>
 * 
 * <p>
 * Producer event subscriber cron that will inject and call the event listener
 * service at a configurable interval. Default sleep interval is 300 seconds (5
 * minutes).
 * 
 * <p>
 * When called the event listener service checks for new message events,
 * validates them, and passes them on for processing.
 */
public class ProducerEventCron {

	private static final Log log = LogFactory.getLog(ProducerEventCron.class);

	// the inteval to sleep for in seconds (300 = 5 minutes)
	private static final int SLEEP_INTERVAL = 300;

	// the maximum number of messages to retrieve and process per interval. 0
	// indicates no limit (all messages available)
	private static final int MAX_MESSAGES = 0;

	private static final String ACCOUNT_ID = "ctt-producer-cms1";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {

		log.debug("Initialize dependencies.");
		// setup the preferred Guice injector for DI
		Injector injector = Guice.createInjector(new SQSS3Module());

		// load the listener
		EventListener listener = injector
				.getInstance(ProducerEventListenerImpl.class);

		log.debug("Start the endless loop.");
		while (true) {

			// check for messages
			listener.checkMessages(ACCOUNT_ID, MAX_MESSAGES);

			log.debug("sleeping for " + SLEEP_INTERVAL + " seconds.");
			Thread.sleep(SLEEP_INTERVAL * 1000);

		}
	}

}
