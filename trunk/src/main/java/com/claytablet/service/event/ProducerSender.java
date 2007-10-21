package com.claytablet.service.event;

import com.claytablet.model.event.producer.ApproveAssetTask;
import com.claytablet.model.event.producer.CancelAsset;
import com.claytablet.model.event.producer.CancelProject;
import com.claytablet.model.event.producer.CancelSupportAsset;
import com.claytablet.model.event.producer.CreateAsset;
import com.claytablet.model.event.producer.CreateSupportAsset;
import com.claytablet.model.event.producer.RejectAssetTask;
import com.claytablet.model.event.producer.SubmitProject;
import com.claytablet.queue.service.QueueServiceException;
import com.claytablet.service.event.impl.ProducerSenderImpl;
import com.claytablet.storage.service.StorageServiceException;
import com.google.inject.ImplementedBy;

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
 * The producer sender is responsible for sending producer specific events and
 * transmitting asset and support asset files to the Clay Tablet platform.
 * 
 * @see ProducerSenderImpl
 * @see ApproveAssetTask
 * @see CancelAsset
 * @see CancelProject
 * @see CancelSupportAsset
 * @see CreateAsset
 * @see CreateSupportAsset
 * @see RejectAssetTask
 * @see SubmitProject
 */
@ImplementedBy(ProducerSenderImpl.class)
public interface ProducerSender {

	/**
	 * Sends an approve asset task event, and uploads it's associated file if a
	 * path is provided. If a file is provided, the with content flag on the
	 * event will be set.
	 * 
	 * @param event
	 *            Required parameter that specifies the approve asset task event
	 *            to send.
	 * @param sourceFilePath
	 *            Optional parameter that specifies the path to the asset task
	 *            file to be uploaded.
	 * @throws EventServiceException
	 * @throws StorageServiceException
	 * @throws QueueServiceException
	 */
	public void sendEvent(ApproveAssetTask event, String sourceFilePath)
			throws EventServiceException, StorageServiceException,
			QueueServiceException;

	/**
	 * Sends a cancel asset event.
	 * 
	 * @param event
	 *            Required parameter that specifies the cancel asset event to
	 *            send.
	 * @throws EventServiceException
	 * @throws QueueServiceException
	 */
	public void sendEvent(CancelAsset event) throws EventServiceException,
			QueueServiceException;

	/**
	 * Sends a cancel project event.
	 * 
	 * @param event
	 *            Required parameter that specifies the cancel project event to
	 *            send.
	 * @throws EventServiceException
	 * @throws QueueServiceException
	 */
	public void sendEvent(CancelProject event) throws EventServiceException,
			QueueServiceException;

	/**
	 * Sends a cancel support asset event.
	 * 
	 * @param event
	 *            Required parameter that specifies the cancel support asset
	 *            event to send.
	 * @throws EventServiceException
	 * @throws QueueServiceException
	 */
	public void sendEvent(CancelSupportAsset event)
			throws EventServiceException, QueueServiceException;

	/**
	 * Sends a create asset event, and uploads it's associated file.
	 * 
	 * @param event
	 *            Required parameter that specifies the create asset event to
	 *            send.
	 * @param sourceFilePath
	 *            Required parameter that specifies the path to the asset file
	 *            to be uploaded.
	 * @throws EventServiceException
	 * @throws StorageServiceException
	 * @throws QueueServiceException
	 */
	public void sendEvent(CreateAsset event, String sourceFilePath)
			throws EventServiceException, StorageServiceException,
			QueueServiceException;

	/**
	 * Sends a create support asset event, and uploads it's associated file.
	 * 
	 * @param event
	 *            Required parameter that specifies the create support asset
	 *            event to send.
	 * @param sourceFilePath
	 *            Required parameter that specifies the path to the support
	 *            asset file to be uploaded.
	 * @throws EventServiceException
	 * @throws StorageServiceException
	 * @throws QueueServiceException
	 */
	public void sendEvent(CreateSupportAsset event, String sourceFilePath)
			throws EventServiceException, StorageServiceException,
			QueueServiceException;

	/**
	 * Sends a reject asset task event, and uploads it's associated file if a
	 * path is provided. If a file is provided, the with content flag on the
	 * event will be set.
	 * 
	 * @param event
	 *            Required parameter that specifies the reject asset task event
	 *            to send.
	 * @param sourceFilePath
	 *            Optional parameter that specifies the path to the asset task
	 *            file to be uploaded.
	 * @throws EventServiceException
	 * @throws StorageServiceException
	 * @throws QueueServiceException
	 */
	public void sendEvent(RejectAssetTask event, String sourceFilePath)
			throws EventServiceException, StorageServiceException,
			QueueServiceException;

	/**
	 * Sends a submit project event. All of the assets and support assets that
	 * belong to the project should be created before the project itself is
	 * submitted.
	 * 
	 * @param event
	 *            Required parameter that specifies the submit project event to
	 *            send.
	 * @throws EventServiceException
	 * @throws QueueServiceException
	 */
	public void sendEvent(SubmitProject event) throws EventServiceException,
			QueueServiceException;
}
