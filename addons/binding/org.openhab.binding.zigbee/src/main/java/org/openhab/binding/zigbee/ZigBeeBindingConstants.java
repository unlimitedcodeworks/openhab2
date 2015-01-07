/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.zigbee;

import java.util.Collection;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

import com.google.common.collect.Lists;

/**
 * The {@link ZigBeeBinding} class defines common constants, which are 
 * used across the whole binding.
 * 
 * @author Christian Arlt - Initial contribution
 */
public class ZigBeeBindingConstants {

    public static final String BINDING_ID = "zigbee";
    
    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_SAMPLE = new ThingTypeUID(BINDING_ID, "sample");
    public final static ThingTypeUID THING_TYPE_ONOFFTEST = new ThingTypeUID(BINDING_ID, "onofftest");
    public final static ThingTypeUID THING_TYPE_0X0009 = new ThingTypeUID(BINDING_ID, "0x0009");
    public final static ThingTypeUID THING_TYPE_BRIDGE = new ThingTypeUID(BINDING_ID, "bridge");
    
    // List of all Channel ids
    public final static String CHANNEL_SAMPLECHANNEL = "samplechannel";
    public final static String CHANNEL_ONOFFCLUSTERCHANNEL = "onoffcluster";
    
    
    
    public final static Collection<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Lists.newArrayList(
    		THING_TYPE_SAMPLE,THING_TYPE_0X0009,THING_TYPE_ONOFFTEST);
    
    
}
