package com.framework.helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static com.framework.config.ConfigHelper.driverConfig;


public class DriverHelper {
    public static void configureDriver() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        DesiredCapabilities capabilities = new DesiredCapabilities();

        Configuration.browser = driverConfig.browserName();
        Configuration.browserVersion = driverConfig.browserVersion();
        Configuration.browserSize = driverConfig.browserSize();

        if (driverConfig.browserName().equalsIgnoreCase("chrome")) {
            capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
        }

        if (driverConfig.webRemoteDriverUrl() != null
                && !driverConfig.webRemoteDriverUrl().isEmpty()) {
            Configuration.remote = driverConfig.webRemoteDriverUrl();
            capabilities.setCapability("enableVNC", driverConfig.enableVnc());
            capabilities.setCapability("enableVideo", driverConfig.enableVideo());
        }
        Configuration.browserCapabilities = capabilities;
    }


    public static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--disable-popup-blocking");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--lang=en-en");

        return chromeOptions;
    }
}
