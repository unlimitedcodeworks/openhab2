package org.openhab.binding.zigbee.handler;

import static org.openhab.binding.zigbee.ZigBeeBindingConstants.THING_TYPE_BRIDGE;

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

	private static final String DEFAULT_USERNAME = "EclipseSmartHome";

	private Logger logger = LoggerFactory.getLogger(ZigBeeBridgeHandler.class);

	//	 private Map<String, FullLight> lastLightStates = new HashMap<>();

	private boolean lastBridgeConnectionState = false;

	//	 private List<LightStatusListener> lightStatusListeners = new CopyOnWriteArrayList<>();

	private ScheduledFuture<?> pollingJob;

	private ZigBeeBridgeDevice zigBeeBridgeDevice;

	/**
	 * Checks for incoming messages from connected ZigBee devices.
	 */
	private Runnable pollingRunnable  = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			logger.info("----------- Runnable");

			if ((new Random().nextInt(10) + 1)%2 == 0) {
				updateState("network_onoff", OnOffType.OFF);
			} else {
				updateState("network_onoff", OnOffType.ON);
			}
			
		}
	};


	public void handleThingCommand(Command command, Configuration configuration){
		
	}

	public ZigBeeBridgeHandler(Bridge bridge) {
		super(bridge);
		logger.info("----------- ZigBeeBridge Hello world! - info");
/*
		if (pollingJob == null || pollingJob.isCancelled()) {
			pollingJob = scheduler.scheduleAtFixedRate(pollingRunnable, 2, POLLING_FREQUENCY, TimeUnit.SECONDS);
		}
		*/
	}

	@Override
	public void handleCommand(ChannelUID channelUID, Command command) {
		// TODO Auto-generated method stub
		logger.info("----------- ZigBeeBridge command! - info" + ", channeluid: "+ channelUID + ", cmd: " + command);
	}

}
