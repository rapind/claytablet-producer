package com.claytablet.service.event.mock;

import com.claytablet.factory.QueuePublisherServiceFactory;
import com.claytablet.factory.StorageClientServiceFactory;
import com.claytablet.model.event.platform.CompletedProject;
import com.claytablet.model.event.platform.ProcessingError;
import com.claytablet.model.event.platform.ReviewAssetTask;
import com.claytablet.model.event.producer.ApproveAssetTask;
import com.claytablet.queue.service.QueueServiceException;
import com.claytablet.service.event.EventServiceException;
import com.claytablet.service.event.ProducerReceiver;
import com.claytablet.service.event.ProducerSender;
import com.claytablet.service.event.impl.AbsEventClientImpl;
import com.claytablet.storage.service.StorageServiceException;
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
 * This is the default implementation for the producer receiver.
 * 
 * <p>
 * @see ProducerReceiver
 * @see AbsEventClientImpl
 */
@Singleton
public class ProducerReceiverMock extends AbsEventClientImpl implements
		ProducerReceiver {

	// we're going to automatically respond to messages
	private ProducerSender producerSender;

	private static final String PLATFORM_ACCOUNT_ID = "ctt-platform";

	// TODO - replace this with your assigned account identifier
	private static final String PRODUCER_ACCOUNT_ID = "ctt-producer-cms1";

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param queuePublisherServiceFactory
	 * @param storageClientServiceFactory
	 * @param producerSender
	 */
	@Inject
	public ProducerReceiverMock(
			QueuePublisherServiceFactory queuePublisherServiceFactory,
			StorageClientServiceFactory storageClientServiceFactory,
			ProducerSender producerSender) {
		this.queuePublisherServiceFactory = queuePublisherServiceFactory;
		this.storageClientServiceFactory = storageClientServiceFactory;
		this.producerSender = producerSender;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.client.ReceiverProducer#receiveEvent(com.claytablet.model.event.platform.CompletedProject)
	 */
	public void receiveEvent(CompletedProject event) {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.client.ReceiverProducer#receiveEvent(com.claytablet.model.event.platform.ProcessingError)
	 */
	public void receiveEvent(ProcessingError event) {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// do nothing

		log.error(event.getErrorMessage());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.client.ReceiverProducer#receiveEvent(com.claytablet.model.event.platform.ReviewAssetTask)
	 */
	public void receiveEvent(ReviewAssetTask event)
			throws StorageServiceException, EventServiceException {

		// If an exception is thrown (I.e. unable to retrieve the file from
		// storage) the event will remain on the queue.

		log.debug(event.getClass().getSimpleName() + " event received.");

		log.debug("Download the latest asset task revision for: "
				+ event.getAssetTaskId());
		String downloadPath = super.downloadLatestAssetTaskVersion(event
				.getTargetAccountId(), event.getAssetTaskId(),
				"./files/received/");

		log.debug("Downloaded an asset task version file to: " + downloadPath);

		log
				.debug("Simulate an asset task approval with review comments and a new revised file.");
		ApproveAssetTask event2 = new ApproveAssetTask();
		event2.setAssetTaskId(event.getAssetTaskId());
		event2.setReviewNote("Test review note.");
		event2.setSourceAccountId(PRODUCER_ACCOUNT_ID);
		event2.setTargetAccountId(PLATFORM_ACCOUNT_ID);

		try {
			producerSender.sendEvent(event2, downloadPath);
		} catch (QueueServiceException e) {
			log.error(e);
		}

	}
}
