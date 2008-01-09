package com.claytablet.service.event.mock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.model.ConnectionContext;
import com.claytablet.model.event.Account;
import com.claytablet.model.event.platform.CompletedProject;
import com.claytablet.model.event.platform.ProcessingError;
import com.claytablet.model.event.platform.ReviewAssetTask;
import com.claytablet.model.event.platform.UpdatedAssetTaskState;
import com.claytablet.model.event.producer.ApproveAssetTask;
import com.claytablet.provider.SourceAccountProvider;
import com.claytablet.queue.service.QueueServiceException;
import com.claytablet.service.event.EventServiceException;
import com.claytablet.service.event.ProducerReceiver;
import com.claytablet.service.event.ProducerSender;
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
 * This is the default implementation for the producer receiver.
 * 
 * <p>
 * @see ProducerReceiver
 */
@Singleton
public class ReceiverMock implements ProducerReceiver {

	private final Log log = LogFactory.getLog(getClass());

	private final ConnectionContext context;

	private final SourceAccountProvider sap;

	private final ProducerSender producerSender;

	private StorageClientService storageClientService;

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param context
	 * @param sap
	 * @param producerSender
	 * @param storageClientService
	 */
	@Inject
	public ReceiverMock(final ConnectionContext context,
			final SourceAccountProvider sap,
			final ProducerSender producerSender,
			StorageClientService storageClientService) {

		this.context = context;
		this.sap = sap;
		this.producerSender = producerSender;
		this.storageClientService = storageClientService;
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

		// initialize the storage client for the source account.
		initStorageClient();

		log.debug("Download the latest asset task revision for: "
				+ event.getAssetTaskId());
		String downloadPath = storageClientService
				.downloadLatestAssetTaskVersion(event.getAssetTaskId(),
						"./files/received/");

		log.debug("Downloaded an asset task version file to: " + downloadPath);

		log
				.debug("Simulate an asset task approval with review comments and a new revised file.");
		ApproveAssetTask event2 = new ApproveAssetTask();
		event2.setAssetTaskId(event.getAssetTaskId());
		event2.setReviewNote("Test review note.");

		try {
			producerSender.sendEvent(event2, downloadPath);
		} catch (QueueServiceException e) {
			log.error(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerReceiver#receiveEvent(com.claytablet.model.event.platform.UpdatedAssetTaskState)
	 */
	public void receiveEvent(UpdatedAssetTaskState event) {
		log.debug(event.getClass().getSimpleName() + " event received.");

		// do nothing

	}

	/**
	 * Initializes the storage client with the source account values
	 * (credentials and defaults).
	 */
	private void initStorageClient() {

		log.debug("Retrieve the source account from the provider.");
		Account sourceAccount = sap.get();

		log.debug("Initialize the storage client service.");
		storageClientService.setPublicKey(sourceAccount.getPublicKey());
		storageClientService.setPrivateKey(sourceAccount.getPrivateKey());
		storageClientService.setStorageBucket(sourceAccount.getStorageBucket());
		storageClientService.setDefaultLocalSourceDirectory(context
				.getSourceDirectory());
		storageClientService.setDefaultLocalTargetDirectory(context
				.getTargetDirectory());
	}
}
