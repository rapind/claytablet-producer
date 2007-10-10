package com.claytablet.service.event;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.claytablet.model.event.platform.CompleteProject;
import com.claytablet.model.event.platform.ProcessingError;
import com.claytablet.model.event.platform.ReviewAssetTask;
import com.claytablet.util.LanguageUtil;

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
 * Tests for the producer receiver. Please edit the producer account identifier.
 * 
 * <p>
 * @see ProducerReceiver
 * @see CompleteProject
 * @see ProcessingError
 * @see ReviewAssetTask
 */
public abstract class AbsProducerReceiverTest extends TestCase {

	protected final Log log = LogFactory.getLog(getClass());

	protected ProducerReceiver receiver;

	protected final String PLATFORM_ACCOUNT_ID = "ctt-platform";

	// TODO - replace this with your assigned account identifier
	protected final String PRODUCER_ACCOUNT_ID = "ctt-producer-cms1";

	// -------------------------------------------------------------------------
	// Initializations
	// -------------------------------------------------------------------------

	/**
	 * This is run before every unit test and is used to setup test variables.
	 */
	@Before
	public void setUp() {

		log.debug("SETUP: ");

	}

	/**
	 * This is run after every unit test and is used to undo changes as
	 * necessary.
	 */
	@After
	public void tearDown() {

		log.debug("TEARDOWN: ");
	}

	// -------------------------------------------------------------------------
	// Tests
	// -------------------------------------------------------------------------

	/**
	 * Test for ProcessingError event reception.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testProcessingError() throws Exception {

		log.debug("Create a mock ProcessingError event.");
		ProcessingError event = new ProcessingError();
		event.setSourceAccountId(PLATFORM_ACCOUNT_ID);
		event.setTargetAccountId(PRODUCER_ACCOUNT_ID);
		event.setErrorMessage("mock-error-message");
		event.setErrorDetails("mock-error-details");

		log.debug("Launch the event.");
		receiver.receiveEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for CompleteProject event reception.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCompleteProject() throws Exception {

		log.debug("Create a mock CompleteProject event.");
		CompleteProject event = new CompleteProject();
		event.setSourceAccountId(PLATFORM_ACCOUNT_ID);
		event.setTargetAccountId(PRODUCER_ACCOUNT_ID);
		event.setProjectNativeId("mock-native-id");
		event.setProjectId("mock-project-id");

		log.debug("Launch the event.");
		receiver.receiveEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for ReviewAssetTask event reception.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReviewAssetTask() throws Exception {

		log.debug("Create a mock ReviewAssetTask event.");
		ReviewAssetTask event = new ReviewAssetTask();
		event.setSourceAccountId(PLATFORM_ACCOUNT_ID);
		event.setTargetAccountId(PRODUCER_ACCOUNT_ID);
		event.setAssetId("mock-asset-id");
		event.setAssetTaskId("mock-asset-task-id");
		event.setFileExt("txt");
		event.setAssetNativeId("mock-native-asset-id");
		event.setTargetLanguageCode(LanguageUtil.getCode("French"));

		log.debug("Upload the asset task version file that is to be reviewed.");
		receiver.uploadAssetTaskVersion(event.getTargetAccountId(), event
				.getAssetTaskId(), event.getFileExt(), "assetTaskVersion.txt");

		log.debug("Launch the event.");
		receiver.receiveEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

}
