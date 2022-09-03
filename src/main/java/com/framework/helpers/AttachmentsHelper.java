package com.framework.helpers;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.framework.config.ConfigHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.framework.config.ConfigHelper.driverConfig;
import static java.lang.String.join;


public class AttachmentsHelper {
	private static final Logger LOG = LoggerFactory.getLogger(AttachmentsHelper.class);

	@Attachment(value = "{attachName}", type = "text/plain")
	public static String attachAsText(String attachName, String message) {
		return message;
	}

	@Attachment(value = "{attachName}", type = "image/png")
	public static byte[] attachScreenshot(String attachName) {
		return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
	}

	@Attachment(value = "Page source", type = "text/html")
	public static byte[] attachPageSource() {
		return WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
	}

	public static void attachVideo(String sessionId) {
		URL videoUrl = getVideoUrl(sessionId);
		if (videoUrl != null) {
			InputStream videoInputStream = null;
			Selenide.sleep(1000);

			for (int i = 0; i < 10; i++) {
				try {
					videoInputStream = videoUrl.openStream();
					break;
				} catch (FileNotFoundException e) {
					Selenide.sleep(1000);
				} catch (IOException e) {
					LOG.warn("[ALLURE VIDEO ATTACHMENT ERROR] Cant attach allure video, {}", videoUrl);
					e.printStackTrace();
				}
			}
			Allure.addAttachment("Video", "video/mp4", videoInputStream, ".mp4");
		}
	}

	public static URL getVideoUrl(String sessionId) {
		String videoUrl = ConfigHelper.driverConfig.videoStorage() + sessionId + ".mp4";
		try {
			return new URL(videoUrl);
		} catch (MalformedURLException e) {
			LOG.warn("[ALLURE VIDEO ATTACHMENT ERROR] Wrong test video url, {}", videoUrl);
			e.printStackTrace();
		}
		return null;
	}

	public static String getSessionId() {
		return ((RemoteWebDriver) WebDriverRunner.getWebDriver()).getSessionId().toString().replace("selenoid", "");
	}

		public static String getConsoleLogs() {
		List<String> webDriverLogs = new ArrayList<>();
		if (driverConfig.browserName().equals("chrome")) {
			webDriverLogs = Selenide.getWebDriverLogs(LogType.BROWSER);
		}
		return join("\n", webDriverLogs);
	}


}

