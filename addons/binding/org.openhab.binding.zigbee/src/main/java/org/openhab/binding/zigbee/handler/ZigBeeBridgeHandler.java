package org.openhab.binding.zigbee.handler;

import static org.openhab.binding.zigbee.ZigBeeBindingConstants.THING_TYPE_BRIDGE;
import static org.openhab.binding.zigbee.ZigBeeBindingConstants.BRIDGE_SERIALPORT;
import static org.openhab.binding.zigbee.ZigBeeBindingConstants.BRIDGE_BAUD;
import static org.openhab.binding.zigbee.ZigBeeBindingConstants.BRIDGE_UID64;


import java.math.BigDecimal;
import java.sql.Time;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;

import org.openhab.binding.zigbee.internal.ZigBeeBridgeDevice;
import org.openhab.binding.zigbee.internal.ZigBeeTelegesis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * {@link ZigBeeBridgeHandler} is the handler for a ZigBee bridge and connects it to
 * the framework. All {@link ZigBeeHandler}s use the {@link ZigBeeBridgeHandler}
 * to execute the actual commands.
 *
 * @author Christian Arlt
 *
 */
public class ZigBeeBridgeHandler extends BaseBridgeHandler {

	public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES =
			Collections.singleton(THING_TYPE_BRIDGE);

	private static final int POLLING_FREQUENCY = 2; // in seconds
	private static final String DEFAULT_SERIALPORT = "COM9";
	private static final String DEFAULT_BAUD = "19200";
	private static final String DEFAULT_UID64 = "000D6F0003E37C7A";

	private Logger logger = LoggerFactory.getLogger(ZigBeeBridgeHandler.class);

	private boolean lastBridgeConnectionState = false;

	private ScheduledFuture<?> pollingJob;

	private ZigBeeBridgeDevice bridge = null;

	/**
	 * Checks for incoming messages from connected ZigBee devices.
	 */
	private Runnable pollingRunnable  = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			logger.debug("----------- Runnable");
		}
	};


	public void handleThingCommand(String thing, ChannelUID channelUID, Command command, Configuration configuration){
		logger.debug("ZigBeeBridgeHandler shall handle command: "
				+ command
				+ ", by: " + thing
				+ ", with uid64: " + (String)configuration.get("uid64")
				+ " on Endpoint: " + (String)configuration.get("endpoint")
				);

		// ohab -> zigbee cmd translator.translate()
		//int profile, int address, int endPoint, int cluster, int cmd, int values
		if(command.equals(OnOffType.OFF)){		
			bridge.sendZCLCommand(0104, (String)configuration.get("uid64"), (String)configuration.get("endpoint"), 0006, 0, 0);
		}
		if(command.equals(OnOffType.ON)){		
			bridge.sendZCLCommand(0104, (String)configuration.get("uid64"), (String)configuration.get("endpoint"), 0006, 1, 0);
		}
	}

	public ZigBeeBridgeHandler(Bridge bridge) {
		super(bridge);
		logger.debug("----------- ZigBeeBridgeHandler ZigBeeBridgeHandler()");

		/*
		if (pollingJob == null || pollingJob.isCancelled()) {
			pollingJob = scheduler.scheduleAtFixedRate(pollingRunnable, 2, POLLING_FREQUENCY, TimeUnit.SECONDS);
		}
		 */
	}

	@Override
	public void handleCommand(ChannelUID channelUID, Command command) {
		// TODO Auto-generated method stub
		logger.debug("----------- ZigBeeBridgeHandler handleCommand(): " + "channeluid: "+ channelUID + ", cmd: " + command);
	}

	@Override
	protected void updateStatus(ThingStatus status) {
		super.updateStatus(status);
		for(Thing child : getThing().getThings()) {
			child.setStatus(status);
		}
	}



	@Override
	public void dispose() {
		logger.debug("----------- ZigBeeBridgeHandler dispose().");

		// todo: clear running jobs

		if (bridge != null) {
			bridge = null;
		}
	}

	@Override
	public void initialize() {
		logger.debug("----------- ZigBeeBridgeHandler initialize()");
		// workaround for issue #92: getHandler() returns NULL after
		// configuration update. :

		if(getConfig().get(BRIDGE_SERIALPORT)==null) {
			getConfig().put(BRIDGE_SERIALPORT, DEFAULT_SERIALPORT);
		}
		
		if(getConfig().get(BRIDGE_BAUD)==null) {
			getConfig().put(BRIDGE_BAUD, DEFAULT_BAUD);
		}
		
		if(getConfig().get(BRIDGE_UID64)==null) {
			getConfig().put(BRIDGE_UID64, DEFAULT_UID64);
		}

		// creates a connection to the bridging device
		logger.debug("----------- ----> ZigBeeBridgeHandler initialize(): bridge==null?");	
		if (bridge == null) {
			logger.debug("----------- ----> ZigBeeBridgeHandler initialize(): bridge==null!");
			this.bridge = new ZigBeeTelegesis(this.getConfig());
		} else {
			logger.debug("----------- ----> ZigBeeBridgeHandler initialize(): bridge!=null!");
		}

		// todo init poll job?

		// workaround for issue #92: getHandler() returns NULL after
		// configuration update. :
		getThing().setHandler(this);
	}

}
