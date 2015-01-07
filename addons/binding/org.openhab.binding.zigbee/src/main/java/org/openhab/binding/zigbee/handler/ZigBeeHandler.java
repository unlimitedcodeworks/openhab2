/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.zigbee.handler;

import static org.openhab.binding.zigbee.ZigBeeBindingConstants.*;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ZigBeeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 * 
 * @author Christian Arlt - Initial contribution
 */
public class ZigBeeHandler extends BaseThingHandler {

	private Logger logger = LoggerFactory.getLogger(ZigBeeHandler.class);
	
	private ZigBeeBridgeHandler zigBeeBridgeHandler;
	

	public ZigBeeHandler(Thing thing) {		
		super(thing);
		logger.info("----------- ZigBeeHandler Hello world! - info");
	}

	@Override
	public void handleCommand(ChannelUID channelUID, Command command) {
		logger.info("----------- ZigBeeHandler command! - info" + "channeluid: "+ channelUID + ", cmd: " + command);

		ZigBeeBridgeHandler zigBeeBridgeHandler = getZigBeeBridgeHandler();

		if (zigBeeBridgeHandler == null) {
			logger.warn("zigbee bridge handler not found. Cannot handle command without bridge.");
			return;
		}

		logger.info("----------- ZigBeeHandler delegating command to bridge! - info");
		zigBeeBridgeHandler.handleCommand(channelUID, command);

		if(channelUID.getId().equals(CHANNEL_ONOFFCLUSTERCHANNEL)) {
			// TODO: handle command
		}
		
		if(channelUID.getId().equals(CHANNEL_SAMPLECHANNEL)) {
			// TODO: handle command
		}
		
	}

	private synchronized ZigBeeBridgeHandler getZigBeeBridgeHandler() {
		if(this.zigBeeBridgeHandler==null) {
			Bridge bridge = getBridge();
			if (bridge == null) {
				return null;
			}
			ThingHandler handler = bridge.getHandler();
			if (handler instanceof ZigBeeBridgeHandler) {
				this.zigBeeBridgeHandler = (ZigBeeBridgeHandler) handler;
				//this.zigBeeBridgeHandler.registerLightStatusListener(this);
			} else {
				return null;
			}
		}
		return this.zigBeeBridgeHandler;
	}

}
