/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.zigbee.internal;

import static org.openhab.binding.zigbee.ZigBeeBindingConstants.BRIDGE_UID64;
import static org.openhab.binding.zigbee.ZigBeeBindingConstants.NODE_UID64;
import static org.openhab.binding.zigbee.ZigBeeBindingConstants.NODE_ENDPOINT;

import java.util.Collections;
import java.util.Set;



import org.openhab.binding.zigbee.handler.ZigBeeHandler;
import org.openhab.binding.zigbee.handler.ZigBeeBridgeHandler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;

import com.google.common.collect.Sets;

/**
 * The {@link ZigBeeHandlerFactory} is responsible for creating things and thing 
 * handlers.
 * 
 * @author Christian Arlt - Initial contribution
 */
public class ZigBeeHandlerFactory extends BaseThingHandlerFactory {

	public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES = Sets.union(
			ZigBeeBridgeHandler.SUPPORTED_THING_TYPES,
			ZigBeeHandler.SUPPORTED_THING_TYPES);

	private Logger logger = LoggerFactory.getLogger(ZigBeeHandlerFactory.class);


	@Override
	public Thing createThing(ThingTypeUID thingTypeUID, Configuration configuration,
			ThingUID thingUID, ThingUID bridgeUID) {

		if (ZigBeeBridgeHandler.SUPPORTED_THING_TYPES.contains(thingTypeUID)) {
			ThingUID zigBeeBridgeUID = getBridgeThingUID(thingTypeUID, thingUID, configuration);

			logger.debug("----------- ZigBeeHandlerFactory createThing(): "
					+ "type: " + thingTypeUID
					+ ", serialport: " + (String) configuration.get("serialPort")
					+ ", baud: " + configuration.get("baud")
					+ ", thingUID: " + thingUID
					+ ", zigBeeBridgeUID: " + zigBeeBridgeUID 
					);

			return super.createThing(thingTypeUID, configuration, zigBeeBridgeUID, null);
		}

		if (ZigBeeHandler.SUPPORTED_THING_TYPES.contains(thingTypeUID)) {
			ThingUID nodeUID64 = getNodeUID(thingTypeUID, thingUID, configuration, bridgeUID);

			logger.debug("----------- ZigBeeHandlerFactory createThing: "
					+ "type: " + thingTypeUID
					+ ", uid64: " + (String) configuration.get("uid64")
					+ ", endpoint: " + (String) configuration.get("endpoint")
					+ ", thingUID: " + thingUID
					+ ", zigBeeBridgeUID: " + bridgeUID 					
					);
			return super.createThing(thingTypeUID, configuration, nodeUID64, bridgeUID);
		}

		throw new IllegalArgumentException("The thing type " + thingTypeUID
				+ " is not supported by the hue binding.");
	}

	@Override
	public boolean supportsThingType(ThingTypeUID thingTypeUID) {
		logger.debug("----------- ZigBeeHandlerFactory supportsThingType()");
		return SUPPORTED_THING_TYPES.contains(thingTypeUID);
	}

	private ThingUID getBridgeThingUID(ThingTypeUID thingTypeUID, ThingUID thingUID,
			Configuration configuration) {
		if (thingUID == null) {
			String bridgeUID64 = (String) configuration.get(BRIDGE_UID64);
			thingUID = new ThingUID(thingTypeUID, bridgeUID64);
		}
		return thingUID;
	}

	private ThingUID getNodeUID(ThingTypeUID thingTypeUID, ThingUID thingUID,
			Configuration configuration, ThingUID bridgeUID) {
		String nodeUID64 = (String) configuration.get(NODE_UID64);
		if (thingUID == null) {
			thingUID = new ThingUID(thingTypeUID, nodeUID64, bridgeUID.getId());
		}
		return thingUID;
	}


	@Override
	protected ThingHandler createHandler(Thing thing) {

		logger.debug("----------- ZigBeeHandlerFactory createHandler!");

		if (ZigBeeBridgeHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
			ZigBeeBridgeHandler handler = new ZigBeeBridgeHandler((Bridge) thing);
			//registerLightDiscoveryService(handler);
			logger.debug("----------- ----> ZigBeeHandlerFactory createHandler: bridge" + ", thing is: "+thing.getThingTypeUID());
			return handler;
		} else if (ZigBeeHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
			logger.debug("----------- ----> ZigBeeHandlerFactory createHandler: normal Thing" + ", thing is: "+thing.getThingTypeUID());
			return new ZigBeeHandler(thing);
		} else {
			return null;
		}
	}

	@Override
	protected synchronized void removeHandler(ThingHandler thingHandler) {
		if (thingHandler instanceof ZigBeeBridgeHandler) {
			super.removeHandler(thingHandler);
		}
	}
}

