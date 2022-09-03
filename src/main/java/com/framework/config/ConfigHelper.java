package com.framework.config;

import org.aeonbits.owner.ConfigFactory;


public class ConfigHelper {

	public static DriverConfig driverConfig = ConfigFactory.create(DriverConfig.class, System.getProperties());
	public static EnvConfig envConfig = ConfigFactory.create(EnvConfig.class, System.getProperties());
}
