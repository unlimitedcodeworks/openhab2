package org.openhab.binding.zigbee.internal;
import java.io.DataOutputStream;
import java.math.BigDecimal;

import org.eclipse.smarthome.config.core.Configuration;
import org.openhab.binding.zigbee.handler.ZigBeeBridgeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//import gnu.io.NRSerialPort;
//import gnu.io.SerialPort;

public class ZigBeeTelegesis implements ZigBeeBridgeDevice {

	private Logger logger = LoggerFactory.getLogger(ZigBeeBridgeHandler.class); 
	//private NRSerialPort serial;
	
	private DataOutputStream outStream;
	
	
	public ZigBeeTelegesis(Configuration configuration) {
		if(configuration == null) {
			logger.debug("----------- ZigBeeTelegesis(): no configuration provided");
		} else {
			//serial = new NRSerialPort((String)configuration.get("serialPort"), (int)configuration.get("baud"));
			//boolean ret = serial.connect();
			//logger.debug("-_-_-_ connected?: " + ret);
			
			//outStream = new DataOutputStream(serial.getOutputStream());
			
			logger.debug("ZigBeeTelegesis(): Would configure Telegesis with serialPort "
			+ (String)configuration.get("serialPort")
			+ " and baud: " + ((BigDecimal)configuration.get("baud")).intValue()
			);
		}
	}
	
	@Override
	public void enableNetwork() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disableNetwork() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendZCLCommand(int profile, String address, String endPoint, int cluster, int cmd, int values){
		// TODO Auto-generated method stub
		logger.debug("ZigBeeTelegesis got sendZCLCommand"
		+ "profile: " + profile
		+ ", address: " + address
		+ ", endpoint: " + endPoint
		+ "cluster: " + cluster
		+ ", command:" + cmd
		+ " and values: " + values
		);
	}

	@Override
	public void poll() {
		// TODO Auto-generated method stub

	}

}
