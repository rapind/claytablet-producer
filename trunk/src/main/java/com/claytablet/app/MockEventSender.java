package com.claytablet.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.model.enm.ContentType;
import com.claytablet.model.enm.FileType;
import com.claytablet.model.event.producer.CreateAsset;
import com.claytablet.model.event.producer.CreateSupportAsset;
import com.claytablet.model.event.producer.SubmitProject;
import com.claytablet.module.MockModule;
import com.claytablet.service.event.ProducerSender;
import com.claytablet.util.IdGenerator;
import com.claytablet.util.LanguageUtil;
import com.google.inject.Guice;
import com.google.inject.Injector;

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
 * Producer event sender that will create and send several assets, support
 * assets, and the wrapping project.
 */
public class MockEventSender {

	private static final Log log = LogFactory
			.getLog(MockEventSender.class);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {

		log.debug("Initialize dependencies.");
		// setup the preferred Guice injector for DI
		Injector injector = Guice.createInjector(new MockModule());

		// retrieve the default producer sender instance via Guice.
		ProducerSender sender = injector.getInstance(ProducerSender.class);

		// generate a project identifier which we will assign to all of our
		// assets and support assets. This will group the assets and support
		// assets together for service routing.
		String projectId = IdGenerator.createId();

		// create the first asset
		CreateAsset asset1 = new CreateAsset();
		asset1.setProjectId(projectId);
		asset1.setAssetNativeId("asset1");
		asset1.setName("First Asset");
		asset1.setTags("first, asset");
		asset1.setDescription("First Asset Description.");
		asset1.setContentType(ContentType.Technical);
		asset1.setFileType(FileType.Text);
		asset1.setFileExt("txt");
		asset1.setSourceLanguageCode(LanguageUtil.getCode("English"));
		String[] targetLanguageCodes = new String[2];
		targetLanguageCodes[0] = LanguageUtil.getCode("French");
		targetLanguageCodes[1] = LanguageUtil.getCode("Spanish");
		asset1.setTargetLanguageCodes(targetLanguageCodes);
		asset1
				.setAdditionalServicesRequest("First Asset - Additional Services Request.");

		// send the event
		sender.sendEvent(asset1, "./files/source/asset1.txt");

		// create the second asset
		CreateAsset asset2 = new CreateAsset();
		asset2.setProjectId(projectId);
		asset2.setAssetNativeId("asset2");
		asset2.setName("Second Asset");
		asset2.setTags("second, asset");
		asset2.setDescription("Second Asset Description.");
		asset2.setContentType(ContentType.Technical);
		asset2.setFileType(FileType.PDF);
		asset2.setFileExt("pdf");
		asset2.setSourceLanguageCode(LanguageUtil.getCode("English"));
		targetLanguageCodes = new String[2];
		targetLanguageCodes[0] = LanguageUtil.getCode("French");
		targetLanguageCodes[1] = LanguageUtil.getCode("Spanish");
		asset2.setTargetLanguageCodes(targetLanguageCodes);
		asset2
				.setAdditionalServicesRequest("Second Asset - Additional Services Request.");

		// send the event
		sender.sendEvent(asset2, "./files/source/asset2.pdf");

		// create the third asset
		CreateAsset asset3 = new CreateAsset();
		asset3.setProjectId(projectId);
		asset3.setAssetNativeId("asset3");
		asset3.setName("Third Asset");
		asset3.setTags("Third, asset");
		asset3.setDescription("Third Asset Description.");
		asset3.setContentType(ContentType.Technical);
		asset3.setFileType(FileType.HTML);
		asset3.setFileExt("html");
		asset3.setSourceLanguageCode(LanguageUtil.getCode("English"));
		targetLanguageCodes = new String[2];
		targetLanguageCodes[0] = LanguageUtil.getCode("French");
		targetLanguageCodes[1] = LanguageUtil.getCode("German");
		asset3.setTargetLanguageCodes(targetLanguageCodes);
		asset3
				.setAdditionalServicesRequest("Third Asset - Additional Services Request.");

		// send the event
		sender.sendEvent(asset3, "./files/source/asset3.html");

		// create the fourth asset
		CreateAsset asset4 = new CreateAsset();
		asset4.setProjectId(projectId);
		asset4.setAssetNativeId("asset4");
		asset4.setName("Fourth Asset");
		asset4.setTags("fourth, asset");
		asset4.setDescription("Fourth Asset Description.");
		asset4.setContentType(ContentType.Technical);
		asset4.setFileType(FileType.XML);
		asset4.setFileExt("xml");
		asset4.setSourceLanguageCode(LanguageUtil.getCode("Spanish"));
		targetLanguageCodes = new String[2];
		targetLanguageCodes[0] = LanguageUtil.getCode("French");
		targetLanguageCodes[1] = LanguageUtil.getCode("English");
		asset4.setTargetLanguageCodes(targetLanguageCodes);
		asset4
				.setAdditionalServicesRequest("Fourth Asset - Additional Services Request.");

		// send the event
		sender.sendEvent(asset4, "./files/source/asset4.xml");

		// create the first support asset
		CreateSupportAsset supportAsset1 = new CreateSupportAsset();
		supportAsset1.setProjectId(projectId);
		supportAsset1.setSupportAssetNativeId("supportAsset1");
		supportAsset1.setName("First Support Asset");
		supportAsset1.setTags("first, support, asset");
		supportAsset1.setDescription("First Support Asset Description");
		supportAsset1.setContentType(ContentType.Technical);
		supportAsset1.setFileType(FileType.Text);
		supportAsset1.setFileExt("txt");

		// send the event
		sender.sendEvent(supportAsset1, "./files/source/supportAsset1.txt");

		// create the second support asset
		CreateSupportAsset supportAsset2 = new CreateSupportAsset();
		// assign it to an asset instead of a project
		supportAsset2.setAssetId(asset2.getAssetId());
		supportAsset2.setSupportAssetNativeId("supportAsset2");
		supportAsset2.setName("Second Support Asset");
		supportAsset2.setTags("second, support, asset");
		supportAsset2.setDescription("Second Support Asset Description");
		supportAsset2.setContentType(ContentType.Technical);
		supportAsset2.setFileType(FileType.Other);
		supportAsset2.setFileExt("dat");

		// send the event
		sender.sendEvent(supportAsset2, "./files/source/supportAsset2.dat");

		// submit the project
		SubmitProject project = new SubmitProject();
		// make sure we assign the project id we generated and assigned to all
		// of the assets already.
		project.setProjectId(projectId);
		project.setProjectNativeId("project-submission-test");
		project.setName("Project Submission Test");
		project.setTags("project, submission, test");
		project.setDescription("Project Submission Test Description.");
		project
				.setAdditionalServicesRequest("Project Submission Test - Additional Services Request.");

		// send the event
		sender.sendEvent(project);

	}

}
