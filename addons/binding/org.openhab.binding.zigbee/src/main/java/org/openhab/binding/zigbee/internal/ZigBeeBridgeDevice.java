/**
 * 
 */
package org.openhab.binding.zigbee.internal;

/**
 * 
 * Interface that defines common functions a device used as a bridge has to offer. 
 * 
 * @author Christian
 *
 */
public interface ZigBeeBridgeDevice {

	public void enableNetwork();
	public void disableNetwork();
		
	public void sendZCLCommand(int profile, String address, String endPoint, int cluster, int cmd, int values);
	public void poll();
	
}
