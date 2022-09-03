package com.framework.config;

import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/env.properties"
})
public interface EnvConfig extends Config {
    @Key("username")
    String username();

    @Key("password")
    String password();

    @Key("web.url")
    String webUrl();

    @Key("api.url")
    String apiUrl();
}