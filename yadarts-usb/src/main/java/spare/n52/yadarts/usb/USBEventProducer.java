/**
 * Copyright 2014 the staff of 52°North Initiative for Geospatial Open
 * Source Software GmbH in their free time
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spare.n52.yadarts.usb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spare.n52.yadarts.entity.InteractionEvent;
import spare.n52.yadarts.event.EventListener;
import spare.n52.yadarts.event.EventProducer;
import spare.n52.yadarts.usb.handler.EmprexEventHandler;
import spare.n52.yadarts.usb.handler.EventHandler;

public class USBEventProducer implements EventProducer, USBEventReceiver {

	private static final Logger logger = LoggerFactory.getLogger(USBEventProducer.class);
	
	private List<EventListener> listeners = new ArrayList<>();
	private USBConnection connection;
	private EventHandler handler;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	public USBEventProducer() {
		initHandlers();
	}

	private void initHandlers() {
		this.handler = new EmprexEventHandler();
	}

	private void initConnection() {
		this.connection = new USBConnection(this);
		this.connection.start();
	}

	@Override
	public void registerEventListener(EventListener el) {
		this.listeners.add(el);
	}

	@Override
	public void removeEventListener(EventListener el) {
		this.listeners.remove(el);
	}
	
	@Override
	public void processEvent(final byte[] rawData) {
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				InteractionEvent event = USBEventProducer.this.handler.createEvent(rawData);
				
				if (event == null) {
					return;
				}
				
				synchronized (USBEventProducer.this) {
					for (EventListener el : USBEventProducer.this.listeners) {
						try {
							el.receiveEvent(event);
						}
						catch (RuntimeException e) {
							logger.warn(e.getMessage(), e);
						}
					}
				}
			}

		});
		
	}

	@Override
	public void start() {
		initConnection();
	}

	@Override
	public void stop() {
		this.connection.shutdown();
	}
	
}
