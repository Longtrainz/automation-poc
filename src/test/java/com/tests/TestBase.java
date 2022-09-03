package com.tests;

import com.codeborne.selenide.Configuration;
import com.framework.helpers.DriverHelper;
import com.framework.helpers.Utils;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.framework.config.ConfigHelper.driverConfig;
import static com.framework.config.ConfigHelper.envConfig;
import static com.framework.helpers.AttachmentsHelper.*;

@ExtendWith({ AllureJunit5.class})
public class TestBase {
	protected Utils utils = new Utils();

	@BeforeAll
	static void setUp() {
		RestAssured.baseURI = envConfig.apiUrl();
		Configuration.baseUrl = envConfig.webUrl();
		Configuration.timeout = 4000;
		DriverHelper.configureDriver();
	}

	@AfterEach
	public void addAttachments() {
		String sessionId = getSessionId();
		attachScreenshot("Last screenshot");
		attachPageSource();
		attachAsText("Browser console logs", getConsoleLogs());

		closeWebDriver();

		if (!driverConfig.videoStorage().equals("")) {
			attachVideo(sessionId);
		}
	}
}
