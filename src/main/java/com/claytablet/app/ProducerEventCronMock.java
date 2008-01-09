package com.claytablet.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.module.ProducerModuleMock;
import com.claytablet.service.event.EventListener;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Copyright 2007 Clay Tablet Technologies Inc.
 * 
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at <a
 * href="http://www.apache.org/licenses/LICENSE-2.0">http://www.apache.org/licenses/LICENSE-2.0</a>
 * 
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * <p>
 * 
 * @author <a href="mailto:drapin@clay-tablet.com">Dave Rapin</a>
 * 
 * <p>
 * Producer event cron that will inject and call the event listener service at a
 * configurable interval. Default sleep interval is 300 seconds (5 minutes).
 * 
 * <p>
 * When called the event listener service checks for new message events,
 * validates them, and passes them to the receiver for processing.
 */
public class ProducerEventCronMock {

	private static final Log log = LogFactory.getLog(ProducerEventCronMock.class);

	// the inteval to sleep for in seconds (300 = 5 minutes)
	private static final int SLEEP_INTERVAL = 300;

	// the maximum number of messages to retrieve and process per interval. 0
	// indicates no limit (all messages available)
	private static final int MAX_MESSAGES = 0;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {

		log.debug("Initialize dependencies.");
		// setup the preferred Guice injector for DI
		// Injector injector = Guice.createInjector(new SQSS3Module());
		Injector injector = Guice.createInjector(new ProducerModuleMock());

		// load the listener
		EventListener listener = injector.getInstance(EventListener.class);

		log.debug("Start the endless loop.");
		while (true) {

			// check for messages
			listener.checkMessages(MAX_MESSAGES);

			log.debug("sleeping for " + SLEEP_INTERVAL + " seconds.");
			Thread.sleep(SLEEP_INTERVAL * 1000);

		}
	}

}
