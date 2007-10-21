package com.claytablet.module;

import com.claytablet.service.event.AccountManager;
import com.claytablet.service.event.ProducerReceiver;
import com.claytablet.service.event.drupal.ProducerReceiverDrupal;
import com.claytablet.service.event.impl.AccountManagerImpl;

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
 * Drupal module for Guice configuration. Extends the SQSS3Module and overrides
 * the default receiver binding for a drupal implementation.
 */
public class DrupalSQSS3Module extends SQSS3Module {

	protected void configure() {

		super.configure();

		// specify account manager binding
		bind(AccountManager.class).to(AccountManagerImpl.class);

		// override the default receiver binding
		bind(ProducerReceiver.class).to(ProducerReceiverDrupal.class);

	}
}
