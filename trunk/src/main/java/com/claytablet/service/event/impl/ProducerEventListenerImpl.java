package com.claytablet.service.event.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.model.event.AbsEvent;
import com.claytablet.model.event.Account;
import com.claytablet.provider.SourceAccountProvider;
import com.claytablet.queue.model.Message;
import com.claytablet.queue.service.QueueServiceException;
import com.claytablet.queue.service.QueueSubscriberService;
import com.claytablet.service.event.EventListener;
import com.claytablet.service.event.EventServiceException;
import com.claytablet.service.event.ProducerReceiver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

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
 * This is the default implementation for the producer event listener.
 * 
 * @see ClientAccountProvider
 * @see QueueSubscriberService
 * @see ProducerReceiver
 */
@Singleton
public class ProducerEventListenerImpl implements EventListener {

	private final Log log = LogFactory.getLog(getClass());

	private SourceAccountProvider sap;

	private QueueSubscriberService queueSubscriberService;

	private ProducerReceiver receiver;

	/**
	 * @param sap
	 * @param queueSubscriberService
	 * @param producerReceiver
	 */
	@Inject
	public ProducerEventListenerImpl(SourceAccountProvider sap,
			QueueSubscriberService queueSubscriberService,
			ProducerReceiver producerReceiver) {
		this.sap = sap;
		this.queueSubscriberService = queueSubscriberService;
		this.receiver = producerReceiver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.app.EventListener#checkMessages(java.lang.String,
	 *      int)
	 */
	public void checkMessages(int maxMessages) throws EventServiceException,
			QueueServiceException {

		log.debug("Retrieve the client account from the provider.");
		Account clientAccount = sap.get();

		log
				.debug("Initialize the subscriber service with the credentials and endpoint.");
		queueSubscriberService.setPublicKey(clientAccount.getPublicKey());
		queueSubscriberService.setPrivateKey(clientAccount.getPrivateKey());
		queueSubscriberService.setEndpoint(clientAccount.getQueueEndpoint());

		log.debug("Check for messages.");
		List<Message> messages = queueSubscriberService
				.receiveMessages(maxMessages);

		log.debug("Found " + messages.size() + " messages.");

		if (messages.size() > 0) {

			log.debug("Loop through the messages found.");
			for (Message message : messages) {

				log.debug(message.getBody());

				try {
					handleMessage(message);

					// If any exceptions occured then the message will
					// remain on the queue. Only if we reach this point will
					// the message be removed.
					log.debug("Done with the message. Delete it.");
					queueSubscriberService.deleteMessage(message);

				} catch (Exception e) {
					log.error(e.getMessage());
				}

				log.debug("Sleep for 5 seconds.");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * Handles processing of an individual messages. The message is deserialized
	 * and reflection is used to call the appropriate method in the
	 * ProducerReceiver.
	 * 
	 * @param message
	 *            Required parameter spcifying the message to handle.
	 * @throws EventServiceException
	 * @throws QueueServiceException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void handleMessage(Message message) throws EventServiceException,
			QueueServiceException, IllegalArgumentException,
			IllegalAccessException {

		log.debug("Transform the message payload to an event.");
		AbsEvent event = AbsEvent.fromXml(message.getBody());

		log
				.debug("Call the appropriate event receiver method via reflection for: "
						+ event.getClass().getSimpleName());

		Method[] methods = receiver.getClass().getMethods();
		for (Method method : methods) {
			Class[] parms = method.getParameterTypes();
			if (parms[0].isInstance(event)) {
				log.debug("We have a matching method. Invoke it.");

				// invoke the method
				Object[] invokeParms = new Object[1];
				invokeParms[0] = event;

				try {
					method.invoke(receiver, invokeParms[0]);
				} catch (InvocationTargetException e2) {
					// If an invocation exception occurs during the call then we
					// will simply log it and continue. The offending message
					// will be deleted.
					log.debug("An error occured during event processing: "
							+ e2.getCause().getMessage());

				}

				// we're done
				break;
			}
		}

	}

}
