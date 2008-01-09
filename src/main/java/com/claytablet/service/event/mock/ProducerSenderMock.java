package com.claytablet.service.event.mock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.model.ConnectionContext;
import com.claytablet.model.event.AbsEvent;
import com.claytablet.model.event.Account;
import com.claytablet.model.event.producer.ApproveAssetTask;
import com.claytablet.model.event.producer.CancelAsset;
import com.claytablet.model.event.producer.CancelProject;
import com.claytablet.model.event.producer.CancelSupportAsset;
import com.claytablet.model.event.producer.CreateAsset;
import com.claytablet.model.event.producer.CreateSupportAsset;
import com.claytablet.model.event.producer.RejectAssetTask;
import com.claytablet.model.event.producer.SubmitProject;
import com.claytablet.provider.SourceAccountProvider;
import com.claytablet.provider.TargetAccountProvider;
import com.claytablet.queue.model.Message;
import com.claytablet.queue.service.QueuePublisherService;
import com.claytablet.queue.service.QueueServiceException;
import com.claytablet.service.event.EventServiceException;
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
 * This is the default implementation for the producer sender.
 * 
 * @see ProducerSender
 * @see SourceAccountProvider
 * @see TargetAccountProvider
 * @see QueuePublisherService
 * @see StorageClientService
 * @see ApproveAssetTask
 * @see CancelAsset
 * @see CancelProject
 * @see CancelSupportAsset
 * @see CreateAsset
 * @see CreateSupportAsset
 * @see RejectAssetTask
 * @see SubmitProject
 * @see AbsEvent
 */
@Singleton
public class ProducerSenderMock implements ProducerSender {

	private final Log log = LogFactory.getLog(getClass());

	private final ConnectionContext context;

	private final SourceAccountProvider sap;

	private final TargetAccountProvider tap;

	private QueuePublisherService queuePublisherService;

	private StorageClientService storageClientService;

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param context
	 * @param sap
	 * @param tap
	 * @param queuePublisherService
	 * @param storageClientService
	 */
	@Inject
	public ProducerSenderMock(final ConnectionContext context,
			final SourceAccountProvider sap, final TargetAccountProvider tap,
			QueuePublisherService queuePublisherService,
			StorageClientService storageClientService) {

		this.context = context;
		this.sap = sap;
		this.tap = tap;
		this.queuePublisherService = queuePublisherService;
		this.storageClientService = storageClientService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.ApproveAssetTask,
	 *      java.lang.String)
	 */
	public void sendEvent(ApproveAssetTask event, String sourceFilePath)
			throws EventServiceException, StorageServiceException,
			QueueServiceException {

		log.debug("Check to see if a file is being uploaded.");
		if (sourceFilePath != null || sourceFilePath.length() > 0) {

			log.debug("Populate file extension using the source file path.");
			event.setFileExt(sourceFilePath.substring(sourceFilePath
					.lastIndexOf(".") + 1, sourceFilePath.length()));

			// initialize the storage client for the source account.
			initStorageClient();

			log.debug("Upload the asset task version file: " + sourceFilePath);
			storageClientService.uploadAssetTaskVersion(event.getAssetTaskId(),
					event.getFileExt(), sourceFilePath);

			log.debug("Make sure the with content flag is set to true.");
			event.setWithContent(true);
		} else {
			log.debug("Make sure the with content flag is set to false.");
			event.setWithContent(false);
		}

		// send the event
		sendEvent((AbsEvent) event);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.CancelAsset)
	 */
	public void sendEvent(CancelAsset event) throws EventServiceException,
			QueueServiceException {

		// send the event
		sendEvent((AbsEvent) event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.CancelProject)
	 */
	public void sendEvent(CancelProject event) throws EventServiceException,
			QueueServiceException {

		// send the event
		sendEvent((AbsEvent) event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.CancelSupportAsset)
	 */
	public void sendEvent(CancelSupportAsset event)
			throws EventServiceException, QueueServiceException {

		// send the event
		sendEvent((AbsEvent) event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.CreateAsset,
	 *      java.lang.String)
	 */
	public void sendEvent(CreateAsset event, String sourceFilePath)
			throws EventServiceException, StorageServiceException,
			QueueServiceException {

		log.debug("Check to make sure a file path has been specified.");
		if (sourceFilePath == null || sourceFilePath.length() == 0) {
			throw new EventServiceException(
					"The source file path was invalid. A file must be specified for upload when creating an asset.");
		}

		log.debug("Populate file extension using the source file path.");
		event.setFileExt(sourceFilePath.substring(sourceFilePath
				.lastIndexOf(".") + 1, sourceFilePath.length()));

		// initialize the storage client for the source account.
		initStorageClient();

		log.debug("Upload the asset: " + event.getAssetId());
		storageClientService.uploadAsset(event.getAssetId(),
				event.getFileExt(), sourceFilePath);

		// send the event
		sendEvent((AbsEvent) event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.CreateSupportAsset,
	 *      java.lang.String)
	 */
	public void sendEvent(CreateSupportAsset event, String sourceFilePath)
			throws EventServiceException, StorageServiceException,
			QueueServiceException {

		log.debug("Check to make sure a file path has been specified.");
		if (sourceFilePath == null || sourceFilePath.length() == 0) {
			throw new EventServiceException(
					"The source file path was invalid. A file must be specified for upload when creating a support asset.");
		}

		log.debug("Populate file extension using the source file path.");
		event.setFileExt(sourceFilePath.substring(sourceFilePath
				.lastIndexOf(".") + 1, sourceFilePath.length()));

		// initialize the storage client for the source account.
		initStorageClient();

		log.debug("Upload the support asset: " + event.getSupportAssetId());
		storageClientService.uploadSupportAsset(event.getSupportAssetId(),
				event.getFileExt(), sourceFilePath);

		// send the event
		sendEvent((AbsEvent) event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.RejectAssetTask,
	 *      java.lang.String)
	 */
	public void sendEvent(RejectAssetTask event, String sourceFilePath)
			throws EventServiceException, StorageServiceException,
			QueueServiceException {

		log.debug("Check to see if a file is being uploaded.");
		if (sourceFilePath != null || sourceFilePath.length() > 0) {

			log.debug("Populate file extension using the source file path.");
			event.setFileExt(sourceFilePath.substring(sourceFilePath
					.lastIndexOf(".") + 1, sourceFilePath.length()));

			// initialize the storage client for the source account.
			initStorageClient();

			// upload the asset task file
			storageClientService.uploadAssetTaskVersion(event.getAssetTaskId(),
					event.getFileExt(), sourceFilePath);

			log.debug("Make sure the with content flag is set to true.");
			event.setWithContent(true);
		} else {
			log.debug("Make sure the with content flag is set to false.");
			event.setWithContent(false);
		}

		// send the event
		sendEvent((AbsEvent) event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.SubmitProject)
	 */
	public void sendEvent(SubmitProject event) throws EventServiceException,
			QueueServiceException {

		if (event.getName() == null || event.getName().equals("")) {
			throw new EventServiceException("Name is a required field.");
		}

		// send the event
		sendEvent((AbsEvent) event);
	}

	/**
	 * Sends an event to the queue.
	 * 
	 * @param event
	 *            Required parameter that specifies the event to send.
	 * @throws EventServiceException
	 * @throws QueueServiceException
	 */
	private void sendEvent(AbsEvent event) throws EventServiceException,
			QueueServiceException {

		log
				.debug("Retrieve the source and target accounts from the providers.");
		Account sourceAccount = sap.get();
		Account targetAccount = tap.get();
		log.debug("Sending to: " + targetAccount.getQueueEndpoint());

		log
				.debug("Populate the source and target account identifiers in the event.");
		event.setSourceAccountId(sourceAccount.getId());
		event.setTargetAccountId(targetAccount.getId());

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		log.debug("Serialize the event to a new message object.");
		Message message = new Message(null, AbsEvent.toXml(event));

		log.debug("Initialize the queue publisher.");
		queuePublisherService.setPublicKey(sourceAccount.getPublicKey());
		queuePublisherService.setPrivateKey(sourceAccount.getPrivateKey());
		queuePublisherService.setEndpoint(targetAccount.getQueueEndpoint());

		log.debug("Send the event message.");
		queuePublisherService.sendMessage(message);
		log.debug("Event sent.");

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
