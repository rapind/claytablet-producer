package com.claytablet.service.event.drupal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.model.event.Account;
import com.claytablet.model.event.platform.CompletedProject;
import com.claytablet.model.event.platform.ProcessingError;
import com.claytablet.model.event.platform.ReviewAssetTask;
import com.claytablet.provider.SourceAccountProvider;
import com.claytablet.service.event.EventServiceException;
import com.claytablet.service.event.ProducerReceiver;
import com.claytablet.storage.service.StorageClientService;
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
 * This is the drupal implementation for the producer receiver.
 * 
 * <p>
 * @see ProducerReceiver
 * @see SourceAccountProvider
 * @see StorageClientService
 */
@Singleton
public class ProducerReceiverDrupal implements ProducerReceiver {

	private final Log log = LogFactory.getLog(getClass());

	private SourceAccountProvider sap;

	private StorageClientService storageClientService;

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param sap
	 * @param storageClientService
	 */
	@Inject
	public ProducerReceiverDrupal(SourceAccountProvider sap,
			StorageClientService storageClientService) {
		this.sap = sap;
		this.storageClientService = storageClientService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.client.ReceiverProducer#receiveEvent(com.claytablet.model.event.platform.CompletedProject)
	 */
	public void receiveEvent(CompletedProject event) {

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

		// retrieve the client account from the provider.
		Account clientAccount = sap.get();

		log.debug("Initialize the storage client service.");
		storageClientService.setPublicKey(clientAccount.getPublicKey());
		storageClientService.setPrivateKey(clientAccount.getPrivateKey());
		storageClientService.setStorageBucket(clientAccount.getStorageBucket());

		log.debug("Download the latest asset task revision for: "
				+ event.getAssetTaskId());
		String downloadPath = storageClientService
				.downloadLatestAssetTaskVersion(event.getAssetTaskId(),
						"./files/received/");

		log.debug("Downloaded an asset task version file to: " + downloadPath);

		// TODO - producer integration code goes here.
		// I.e. send the asset to the CMS and mark it for review.

		// If an exception is thrown the event will remain on the queue.

	}
}
