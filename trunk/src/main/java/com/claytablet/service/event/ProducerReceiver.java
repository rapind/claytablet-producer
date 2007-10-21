package com.claytablet.service.event;

import com.claytablet.model.event.platform.CompletedProject;
import com.claytablet.model.event.platform.ProcessingError;
import com.claytablet.model.event.platform.ReviewAssetTask;
import com.claytablet.service.event.impl.ProducerReceiverImpl;
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
 * The producer receiver is reponsible for trapping and processing producer
 * specific events sent by the Clay Tablet platform.
 * 
 * <p>
 * Implementations of this interface should contain producer specific to hook up
 * to native CMS systems etc.
 * 
 * <p>
 * @see ProducerReceiverImpl
 * @see CompleteProject
 * @see ProcessingError
 * @see ReviewAssetTask
 */
@ImplementedBy(ProducerReceiverImpl.class)
public interface ProducerReceiver {

	/**
	 * Receives a completed project event.
	 * 
	 * <p>
	 * Once all of the tasks for a project have been approved or concelled the
	 * project is marked as completed and this event will be launched by the
	 * platform. At this point a call back to the CMS could be made to flag the
	 * project or bundle as being completed or ready for the next workflow step.
	 * 
	 * @param event
	 *            The event to process.
	 */
	public void receiveEvent(CompletedProject event);

	/**
	 * Receives a processing error event.
	 * 
	 * <p>
	 * These are errors that occur while processing an event that has been sent
	 * to the Clay Tablet Platform. The processing error should be examined
	 * since it will usually indicate necessary action by the producer. I.e. If
	 * an asset was created but could not be processed because the file had not
	 * been uploaded.
	 * 
	 * @param event
	 *            The event to process.
	 */
	public void receiveEvent(ProcessingError event);

	/**
	 * Receives a review asset task event.
	 * 
	 * <p>
	 * Once a provider has submitted their work on an asset this event will be
	 * launched. The work (asset task) can now be approved or rejected.
	 * 
	 * @param event
	 *            The event to process.
	 * @throws StorageServiceException
	 * @throws EventServiceException
	 */
	public void receiveEvent(ReviewAssetTask event)
			throws StorageServiceException, EventServiceException;
}
