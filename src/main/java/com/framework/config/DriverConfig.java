package com.framework.config;

import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/remote_driver.properties",
        "classpath:config/local_driver.properties"
})
public interface DriverConfig extends Config {
    @DefaultValue("chrome")
    @Key("browser.name")
    String browserName();

    @Key("browser.version")
    String browserVersion();

    @Key("browser.size")
    String browserSize();

    @Key("web.remote.driver.url")
    String webRemoteDriverUrl();

    @Key("video.storage")
    String videoStorage();

    @Key("enable.video")
    boolean enableVideo();

    @Key("enable.vnc")
    boolean enableVnc();
}