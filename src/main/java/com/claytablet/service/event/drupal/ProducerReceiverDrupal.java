package com.claytablet.service.event.drupal;

import com.claytablet.factory.QueuePublisherServiceFactory;
import com.claytablet.factory.StorageClientServiceFactory;
import com.claytablet.model.event.platform.CompleteProject;
import com.claytablet.model.event.platform.ProcessingError;
import com.claytablet.model.event.platform.ReviewAssetTask;
import com.claytablet.service.event.EventServiceException;
import com.claytablet.service.event.ProducerReceiver;
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
 * the License at
 * 
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * This is the drupal implementation for the producer receiver.
 * 
 * <p>
 * @see ProducerReceiver
 * @see AbsEventClientImpl
 */
@Singleton
public class ProducerReceiverDrupal extends AbsEventClientImpl implements
		ProducerReceiver {

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param queuePublisherServiceFactory
	 * @param storageClientServiceFactory
	 */
	@Inject
	public ProducerReceiverDrupal(
			QueuePublisherServiceFactory queuePublisherServiceFactory,
			StorageClientServiceFactory storageClientServiceFactory) {
		this.queuePublisherServiceFactory = queuePublisherServiceFactory;
		this.storageClientServiceFactory = storageClientServiceFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.client.ReceiverProducer#receiveEvent(com.claytablet.model.event.platform.CompleteProject)
	 */
	public void receiveEvent(CompleteProject event) {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// TODO - producer integration code goes here.
		// I.e. mark the local project as completed in the CMS.

		// If an exception is thrown the event will remain on the queue.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.client.ReceiverProducer#receiveEvent(com.claytablet.model.event.platform.ProcessingError)
	 */
	public void receiveEvent(ProcessingError event) {

		log.debug(event.getClass().getSimpleName() + " event received.");

		// TODO - producer integration code goes here.
		// An error occured. Examine and take appropriate action.

		log.error(event.getErrorMessage());

		// The event.getErrorDetails() will contain the original serialized
		// event that caused the error. It can be deserialized into it's event
		// object and dealt with.

		// If an exception is thrown the event will remain on the queue.
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
		super.downloadLatestAssetTaskVersion(event.getTargetAccountId(), event
				.getAssetTaskId());

		// TODO - producer integration code goes here.
		// I.e. send the asset to the CMS and mark it for review.

		// If an exception is thrown the event will remain on the queue.
	}
}
