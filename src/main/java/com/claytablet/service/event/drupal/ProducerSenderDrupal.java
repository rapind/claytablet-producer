package com.claytablet.service.event.drupal;

import com.claytablet.factory.QueuePublisherServiceFactory;
import com.claytablet.factory.StorageClientServiceFactory;
import com.claytablet.model.event.producer.ApproveAssetTask;
import com.claytablet.model.event.producer.CancelAsset;
import com.claytablet.model.event.producer.CancelAssetTask;
import com.claytablet.model.event.producer.CancelProject;
import com.claytablet.model.event.producer.CancelSupportAsset;
import com.claytablet.model.event.producer.CreateAsset;
import com.claytablet.model.event.producer.CreateSupportAsset;
import com.claytablet.model.event.producer.RejectAssetTask;
import com.claytablet.model.event.producer.SubmitProject;
import com.claytablet.queue.service.QueueServiceException;
import com.claytablet.service.event.EventServiceException;
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
 * This is the drupal implementation for the producer sender.
 * 
 * @see ProducerSender
 * @see AbsEventClientImpl
 */
@Singleton
public class ProducerSenderDrupal extends AbsEventClientImpl implements
		ProducerSender {

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param queuePublisherServiceFactory
	 * @param storageClientServiceFactory
	 */
	@Inject
	public ProducerSenderDrupal(
			QueuePublisherServiceFactory queuePublisherServiceFactory,
			StorageClientServiceFactory storageClientServiceFactory) {
		this.queuePublisherServiceFactory = queuePublisherServiceFactory;
		this.storageClientServiceFactory = storageClientServiceFactory;
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

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		log.debug("Check to see if a file is being uploaded.");
		if (sourceFilePath != null || sourceFilePath.length() > 0) {

			log.debug("Populate file extension using the source file path.");
			event.setFileExt(sourceFilePath.substring(sourceFilePath
					.lastIndexOf("."), sourceFilePath.length()));

			log.debug("Upload the asset task version file.");
			super.uploadAssetTaskVersion(event.getSourceAccountId(), event
					.getAssetTaskId(), event.getFileExt(), sourceFilePath);

			log.debug("Make sure the with content flag is set to true.");
			event.setWithContent(true);
		} else {
			log.debug("Make sure the with content flag is set to false.");
			event.setWithContent(false);
		}

		// send the event
		super.sendEvent(event);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.CancelAsset)
	 */
	public void sendEvent(CancelAsset event) throws EventServiceException,
			QueueServiceException {

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		// send the event
		super.sendEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.CancelAssetTask)
	 */
	public void sendEvent(CancelAssetTask event) throws EventServiceException,
			QueueServiceException {

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		// send the event
		super.sendEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.CancelProject)
	 */
	public void sendEvent(CancelProject event) throws EventServiceException,
			QueueServiceException {

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		// send the event
		super.sendEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.CancelSupportAsset)
	 */
	public void sendEvent(CancelSupportAsset event)
			throws EventServiceException, QueueServiceException {

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		// send the event
		super.sendEvent(event);
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

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		log.debug("Check to make sure a file path has been specified.");
		if (sourceFilePath == null || sourceFilePath.length() == 0) {
			throw new EventServiceException(
					"The source file path was invalid. A file must be specified for upload when creating an asset.");
		}

		log.debug("Populate file extension using the source file path.");
		event.setFileExt(sourceFilePath.substring(sourceFilePath
				.lastIndexOf("."), sourceFilePath.length()));

		log.debug("Upload the asset: " + event.getAssetId());
		super.uploadObject(event.getSourceAccountId(), event.getAssetId() + "."
				+ event.getFileExt(), sourceFilePath);

		// send the event
		super.sendEvent(event);
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

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		log.debug("Check to make sure a file path has been specified.");
		if (sourceFilePath == null || sourceFilePath.length() == 0) {
			throw new EventServiceException(
					"The source file path was invalid. A file must be specified for upload when creating a support asset.");
		}

		log.debug("Populate file extension using the source file path.");
		event.setFileExt(sourceFilePath.substring(sourceFilePath
				.lastIndexOf("."), sourceFilePath.length()));

		log.debug("Upload the support asset: " + event.getSupportAssetId());
		super.uploadObject(event.getSourceAccountId(), event
				.getSupportAssetId()
				+ "." + event.getFileExt(), sourceFilePath);

		// send the event
		super.sendEvent(event);
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

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		log.debug("Check to see if a file is being uploaded.");
		if (sourceFilePath != null || sourceFilePath.length() > 0) {

			log.debug("Populate file extension using the source file path.");
			event.setFileExt(sourceFilePath.substring(sourceFilePath
					.lastIndexOf("."), sourceFilePath.length()));

			// upload the asset task file
			super.uploadAssetTaskVersion(event.getTargetAccountId(), event
					.getAssetTaskId(), event.getFileExt(), sourceFilePath);

			log.debug("Make sure the with content flag is set to true.");
			event.setWithContent(true);
		} else {
			log.debug("Make sure the with content flag is set to false.");
			event.setWithContent(false);
		}

		// send the event
		super.sendEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.ProducerSender#sendEvent(com.claytablet.model.event.producer.SubmitProject)
	 */
	public void sendEvent(SubmitProject event) throws EventServiceException,
			QueueServiceException {

		log.debug("Run event field validation.");
		String validate = event.validate();
		if (validate != null) {
			throw new EventServiceException(validate);
		}

		// send the event
		super.sendEvent(event);
	}

}
