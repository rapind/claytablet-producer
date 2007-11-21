package com.claytablet.module;

import com.claytablet.service.event.EventListener;
import com.claytablet.service.event.impl.ProducerEventListenerImpl;

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
 * Producer test module for Guice configuration. Uses mocked out implementations
 * for queue and storage operations inherited from MockModule.
 */
public class ProducerTestModule extends MockModule {

	protected void configure() {

		super.configure();

		bind(EventListener.class).to(ProducerEventListenerImpl.class);

	}
}
