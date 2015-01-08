/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.zigbee.handler;

import static org.openhab.binding.zigbee.ZigBeeBindingConstants.THING_TYPE_0X0009;
import static org.openhab.binding.zigbee.ZigBeeBindingConstants.THING_TYPE_SAMPLE;
import static org.openhab.binding.zigbee.ZigBeeBindingConstants.CHANNEL_SAMPLE;
import static org.openhab.binding.zigbee.ZigBeeBindingConstants.CHANNEL_ONOFFCLUSTER;
import static org.openhab.binding.zigbee.ZigBeeBindingConstants.NODE_UID64;
import static org.openhab.binding.zigbee.ZigBeeBindingConstants.NODE_ENDPOINT;

import java.util.Set;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

/**
 * The {@link ZigBeeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 * 
 * @author Christian Arlt - Initial contribution
 */
public class ZigBeeHandler extends BaseThingHandler {


	public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES = Sets.newHashSet(THING_TYPE_SAMPLE, THING_TYPE_0X0009);

	private Logger logger = LoggerFactory.getLogger(ZigBeeHandler.class);

	private ZigBeeBridgeHandler bridgeHandler;

	private String nodeUID64;
	private String nodeEndpoint;

	public ZigBeeHandler(Thing thing) {		
		super(thing);
		logger.debug("----------- ZigBeeHandler ZigBeeHandler()");
	}


	@Override
	public void initialize() {
		logger.debug("----------- ZigBeeHandler initialize()");

		final String configNodeUID64 = (String) getConfig().get(NODE_UID64);
		final String configNodeEndpoint = (String) getConfig().get(NODE_ENDPOINT);

		if ((configNodeUID64 != null) && (configNodeEndpoint != null)) {
			nodeUID64 = configNodeUID64;
			nodeEndpoint = configNodeEndpoint;

			logger.debug("----------- ----> ZigBeeHandler initialize(): Thing configured with " + (String) getConfig().get(NODE_UID64) + ", " + (String) getConfig().get(NODE_ENDPOINT));	

			// note: this call implicitly registers our handler as a listener on the bridge
			if(getZigBeeBridgeHandler()!=null) {
				logger.debug("----------- ----> ZigBeeHandler initialize(): bridge available, setting now ");
				getThing().setStatus(getBridge().getStatus());

			} else {
				logger.debug("----------- ----> ZigBeeHandler initialize(): no bridge available ");						
			}
		}
	}

	@Override
	public void dispose() {
		logger.debug("----------- ZigBeeHandler dispose()");
		if ((nodeUID64 != null) && (nodeEndpoint != null)) {
			ZigBeeBridgeHandler bridgeHandler = getZigBeeBridgeHandler();
			if(bridgeHandler!=null) {
				//getZigBeeBridgeHandler().unregisterLightStatusListener(this);
				logger.debug("----------- ----> dispose(): disposing bridgeHandler");		
			}
			nodeUID64 = null;
			nodeEndpoint = null;
		}
	}


	@Override
	public void handleCommand(ChannelUID channelUID, Command command) {
		logger.debug("----------- ZigBeeHandler handleCommand(): " + "channeluid: "+ channelUID + ", cmd: " + command);

		ZigBeeBridgeHandler zigBeeBridgeHandler = getZigBeeBridgeHandler();

		if (zigBeeBridgeHandler == null) {
			logger.warn("----------- ZigBeeHandler handleCommand(): zigbee bridge handler not found. Cannot handle command without bridge.");
			return;
		}

		logger.debug("----------- ----> ZigBeeHandler handleCommand(): delegating command to bridge!");
		zigBeeBridgeHandler.handleThingCommand(this.getThing().getName(), channelUID, command, this.getConfig());
		//bridgeHandler.handleCommand(channelUID, command);		
		/*
		if(channelUID.getId().equals(CHANNEL_ONOFFCLUSTERCHANNEL)) {
			// TODO: handle command
		}

		if(channelUID.getId().equals(CHANNEL_SAMPLECHANNEL)) {
			// TODO: handle command
		}
		 */
		
	}

	private synchronized ZigBeeBridgeHandler getZigBeeBridgeHandler() {
		logger.debug("----------- ZigBeeHandler getZigBeeBridgeHandler()");
		
		if(this.bridgeHandler==null) {
			Bridge bridge = getBridge();
			if (bridge == null) {
				logger.debug("----------- ----> ZigBeeHandler getZigBeeBridgeHandler(): bridge == null!");
				return null;
			}
			
			ThingHandler handler = bridge.getHandler();
			if (handler instanceof ZigBeeBridgeHandler) {
				this.bridgeHandler = (ZigBeeBridgeHandler) handler;
				//this.bridgeHandler.registerLightStatusListener(this);
			} else {
				return null;
			}
		}
		return this.bridgeHandler;
	}


}
