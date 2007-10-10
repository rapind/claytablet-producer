package com.claytablet.service.event;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.claytablet.model.enm.ContentType;
import com.claytablet.model.enm.FileType;
import com.claytablet.model.event.producer.ApproveAssetTask;
import com.claytablet.model.event.producer.CancelAsset;
import com.claytablet.model.event.producer.CancelAssetTask;
import com.claytablet.model.event.producer.CancelProject;
import com.claytablet.model.event.producer.CancelSupportAsset;
import com.claytablet.model.event.producer.CreateAsset;
import com.claytablet.model.event.producer.CreateSupportAsset;
import com.claytablet.model.event.producer.RejectAssetTask;
import com.claytablet.model.event.producer.SubmitProject;
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
 * Tests for the producer sender. Please edit the producer account identifier.
 * 
 * <p>
 * @see ProducerSender
 * @see ApproveAssetTask
 * @see CancelAsset
 * @see CancelAssetTask
 * @see CancelProject
 * @see CancelSupportAsset
 * @see CreateAsset
 * @see CreateSupportAsset
 * @see RejectAssetTask
 * @see SubmitProject
 */
public abstract class AbsProducerSenderTest extends TestCase {

	protected final Log log = LogFactory.getLog(getClass());

	protected ProducerSender sender;

	protected final String PLATFORM_ACCOUNT_ID = "ctt-platform";

	// replace this with the producer cms account identifier.
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
	 * Test for ApproveAssetTask event sending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testApproveAssetTask() throws Exception {

		log.debug("Create a mock ApproveAssetTask event.");
		ApproveAssetTask event = new ApproveAssetTask();
		event.setSourceAccountId(PRODUCER_ACCOUNT_ID);
		event.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event.setAssetTaskId("mock-asset-task-id");
		event.setFileExt("txt");
		event.setReviewNote("mock-review-note");
		event.setWithContent(true);

		// specify the file to be sent, using the default source directory
		String sourceFilePath = "assetTaskVersion.txt";

		sender.sendEvent(event, sourceFilePath);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for CancelAsset event sending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCancelAsset() throws Exception {

		log.debug("Create a mock CancelAsset event.");
		CancelAsset event = new CancelAsset();
		event.setSourceAccountId(PRODUCER_ACCOUNT_ID);
		event.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event.setAssetId("mock-asset-id");

		sender.sendEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for CancelAssetTask event sending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCancelAssetTask() throws Exception {

		log.debug("Create a mock CancelAssetTask event.");
		CancelAssetTask event = new CancelAssetTask();
		event.setSourceAccountId(PRODUCER_ACCOUNT_ID);
		event.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event.setAssetTaskId("mock-asset-task-id");

		sender.sendEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for CancelProject event sending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCancelProject() throws Exception {

		log.debug("Create a mock CancelProject event.");
		CancelProject event = new CancelProject();
		event.setSourceAccountId(PRODUCER_ACCOUNT_ID);
		event.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event.setProjectId("mock-project-id");

		sender.sendEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for CancelSupportAsset event sending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCancelSupportAsset() throws Exception {

		log.debug("Create a mock CancelSupportAsset event.");
		CancelSupportAsset event = new CancelSupportAsset();
		event.setSourceAccountId(PRODUCER_ACCOUNT_ID);
		event.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event.setSupportAssetId("mock-support-asset-id");

		sender.sendEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for CreateAsset event sending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateAsset() throws Exception {

		log.debug("Create a mock CreateAsset event.");
		CreateAsset event = new CreateAsset();
		event.setSourceAccountId(PRODUCER_ACCOUNT_ID);
		event.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event.setProjectId("mock-project-id");
		event.setAssetNativeId("mock-native-id");
		event.setName("mock-name");
		event.setTags("tag1, tag2");
		event.setDescription("mock-description");
		event.setContentType(ContentType.Technical);
		event.setFileType(FileType.Text);
		event.setFileExt("txt");
		event.setSourceLanguageCode(LanguageUtil.getCode("English"));

		String[] targetLanguageCodes = new String[2];
		targetLanguageCodes[0] = LanguageUtil.getCode("French");
		targetLanguageCodes[1] = LanguageUtil.getCode("Spanish");
		event.setTargetLanguageCodes(targetLanguageCodes);

		event.setAdditionalServicesRequest("mock-additional-service-request");

		// specify the file to be sent, using the default source directory
		String sourceFilePath = "asset.txt";

		sender.sendEvent(event, sourceFilePath);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for CreateSupportAsset event sending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateSupportAsset() throws Exception {

		log.debug("Create a mock CreateSupportAsset event.");
		CreateSupportAsset event = new CreateSupportAsset();
		event.setSourceAccountId(PRODUCER_ACCOUNT_ID);
		event.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event.setProjectId("mock-project-id");
		event.setAssetId("mock-asset-id");
		event.setSupportAssetNativeId("mock-native-id");
		event.setName("mock-name");
		event.setTags("tag1, tag2");
		event.setDescription("mock-description");
		event.setContentType(ContentType.Technical);
		event.setFileType(FileType.Text);
		event.setFileExt("txt");

		// specify the file to be sent, using the default source directory
		String sourceFilePath = "supportAsset.txt";

		sender.sendEvent(event, sourceFilePath);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for RejectAssetTask event sending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRejectAssetTask() throws Exception {

		log.debug("Create a mock RejectAssetTask event.");
		RejectAssetTask event = new RejectAssetTask();
		event.setSourceAccountId(PRODUCER_ACCOUNT_ID);
		event.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event.setAssetTaskId("mock-asset-task-id");
		event.setFileExt("txt");
		event.setReviewNote("mock-review-note");
		event.setWithContent(true);

		// specify the file to be sent, using the default source directory
		String sourceFilePath = "assetTaskVersion.txt";

		sender.sendEvent(event, sourceFilePath);

		log.debug("Assertions.");
		assertTrue(true);

	}

	/**
	 * Test for SubmitProject event sending.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSubmitProject() throws Exception {

		log.debug("Create a mock SubmitProject event.");
		SubmitProject event = new SubmitProject();
		event.setSourceAccountId(PRODUCER_ACCOUNT_ID);
		event.setTargetAccountId(PLATFORM_ACCOUNT_ID);
		event.setProjectId("mock-project-id");
		event.setProjectNativeId("mock-native-id");
		event.setName("mock-name");
		event.setTags("tag1, tag2");
		event.setDescription("mock-description");
		event.setAdditionalServicesRequest("mock-additional-service-request");

		sender.sendEvent(event);

		log.debug("Assertions.");
		assertTrue(true);

	}
}
