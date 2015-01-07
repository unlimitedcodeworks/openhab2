/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.zigbee.internal;

import static org.openhab.binding.zigbee.ZigBeeBindingConstants.*;

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

/**
 * The {@link ZigBeeHandlerFactory} is responsible for creating things and thing 
 * handlers.
 * 
 * @author Christian Arlt - Initial contribution
 */
public class ZigBeeHandlerFactory extends BaseThingHandlerFactory {
	//private final static Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections.singleton(THING_TYPE_SAMPLE);

	private Logger logger = LoggerFactory.getLogger(ZigBeeHandlerFactory.class);

	@Override
	public boolean supportsThingType(ThingTypeUID thingTypeUID) {
		logger.info("----------- ZigBeeHandlerFactory supportsThingType! - info" + ", type: " + thingTypeUID);
		return true;
		//return (SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID) || THING_TYPE_BRIDGE.equals(thingTypeUID));
	}


	@Override
	public Thing createThing(ThingTypeUID thingTypeUID, Configuration configuration,
			ThingUID thingUID, ThingUID bridgeUID) {
				
		if (ZigBeeBridgeHandler.SUPPORTED_THING_TYPES.contains(thingTypeUID)) {
			ThingUID zigBeeBridgeUID = getBridgeThingUID(thingTypeUID, thingUID, configuration);
			
			logger.info("----------- ZigBeeHandlerFactory createThing! - info" + ", type: " + thingTypeUID + ", serialport: " + (String) configuration.get("serialPort") + ", baud: " + configuration.get("baud"));
			
			return super.createThing(thingTypeUID, configuration, zigBeeBridgeUID, null);
		}
		
		if (SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID)) {
			//ThingUID hueLightUID = getLightUID(thingTypeUID, thingUID, configuration, bridgeUID);
			logger.info("----------- ZigBeeHandlerFactory createThing! - info" + ", type: " + thingTypeUID + ", uid64: " + (String) configuration.get("uid64") + ", nwa: " + (String) configuration.get("nwa"));
			return super.createThing(thingTypeUID, configuration, thingUID, bridgeUID);
		}
		
		throw new IllegalArgumentException("The thing type " + thingTypeUID
				+ " is not supported by the hue binding.");
	}



	private ThingUID getBridgeThingUID(ThingTypeUID thingTypeUID, ThingUID thingUID,
			Configuration configuration) {
		if (thingUID == null) {
			//String serialNumber = (String) configuration.get(SERIAL_NUMBER);
			thingUID = new ThingUID(thingTypeUID, "TESTID");
		}
		return thingUID;
	}


	@Override
	protected ThingHandler createHandler(Thing thing) {

		logger.info("----------- ZigBeeHandlerFactory createHandler! - info");

		if (ZigBeeBridgeHandler.SUPPORTED_THING_TYPES.contains(thing.getThingTypeUID())) {
			ZigBeeBridgeHandler handler = new ZigBeeBridgeHandler((Bridge) thing);
			//registerLightDiscoveryService(handler);
			logger.info("            --------- do ZigBeeBridgeHandler! - info" + ", thing is: "+thing.getThingTypeUID());
			return handler;
		} else if (supportsThingType(thing.getThingTypeUID())) {
			logger.info("            --------- do ZigBeeHandler! - info" + ", thing is: "+thing.getThingTypeUID());
			return new ZigBeeHandler(thing);
		} else {
			return null;
		}
	}
}

