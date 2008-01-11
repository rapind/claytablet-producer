package com.claytablet.service.event.stubs;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.model.AssetTaskMap;
import com.claytablet.model.AssetTaskMapping;
import com.claytablet.model.ConnectionContext;
import com.claytablet.model.LanguageMap;
import com.claytablet.model.event.AbsEvent;
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
 * This is the Mock implementation for a connected system.
 */
@Singleton
public class MockStub {

	private final Log log = LogFactory.getLog(getClass());

	private final ConnectionContext context;

	private final LanguageMap languageMap;

	private AssetTaskMap assetTaskMap;

	/**
	 * Constructor for dependency injection.
	 * 
	 * @param context
	 * @param languageMap
	 * @param assetTaskMap
	 */
	@Inject
	public MockStub(final ConnectionContext context,
			final LanguageMap languageMap, AssetTaskMap assetTaskMap) {

		this.context = context;
		this.languageMap = languageMap;
		this.assetTaskMap = assetTaskMap;
	}

	/**
	 * Prints out the connection params and mappings that were injected.
	 * 
	 * @throws IOException
	 */
	public void logConfig() throws IOException {

		log.debug("** Connection parameters **");
		Hashtable<String, String> parms = context.getConnectionParms();
		for (String key : parms.keySet()) {
			log.debug(key + ": " + parms.get(key));
		}

		log.debug("** Language mappings **");
		for (String key : languageMap.keys()) {
			log.debug(key + ": " + languageMap.get(key));
		}

		log.debug("** Asset Task mappings **");
		for (String key : assetTaskMap.keys()) {
			log.debug("Connector Asset Task ID -- " + key + " --");
			AssetTaskMapping mapping = assetTaskMap.get(key);
			log.debug("CTT Asset Task ID: " + mapping.getCttAssetTaskId());
			log.debug("CTT Project ID: " + mapping.getCttProjectId());
			log.debug("Connector Project ID: "
					+ mapping.getConnectorProjectId());
			log.debug("Source language: " + mapping.getSourceLanguageCode());
			log.debug("Target language: " + mapping.getTargetLanguageCode());

		}

	}

	/**
	 * Dummy method. To be replaced with proper stub methods.
	 * 
	 * @param event
	 * @param downloadPath
	 */
	public void doSomething(AbsEvent event, String downloadPath) {

		log.debug("Event type: " + event.getClass() + ", downloadPath: "
				+ downloadPath);

		// Within the stub you have access to the following objects through
		// dependency injection:
		// 1. The connection context parameters which are hold connection
		// information specific to a provider system.
		// 2. The language mappings which are used to map clay tablet language
		// codes to provider specific language codes.
		// 3. The asset task mappings which are used to map clay tablet asset
		// task identifier to provider specific identifiers and projects.

		// Event fields and new file paths can be passed in from the receiver or
		// poller as needed.

	}

}
