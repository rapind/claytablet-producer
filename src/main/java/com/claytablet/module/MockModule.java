package com.claytablet.module;

import com.claytablet.service.event.EventListener;
import com.claytablet.service.event.ProducerEventListener;
import com.claytablet.service.event.ProducerReceiver;
import com.claytablet.service.event.ProducerStatePoller;
import com.claytablet.service.event.impl.MockReceiver;
import com.claytablet.service.event.impl.MockStatePoller;

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
 * Mock module for Guice configuration. Extends the SQSS3Module and specifies
 * the event listener, producer receiver, and producer state poller bindings.
 */
public class MockModule extends SQSS3Module {

	protected void configure() {

		super.configure();

		bind(EventListener.class).to(ProducerEventListener.class);

		bind(ProducerReceiver.class).to(MockReceiver.class);
		bind(ProducerStatePoller.class).to(MockStatePoller.class);

	}
}
