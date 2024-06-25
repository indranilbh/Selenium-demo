package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BasePage {

	private String url;
	private Properties prop;
	public static String screenShotDestinationPath;

	public BasePage() throws IOException {
		prop = new Properties();
		FileInputStream data = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\resources\\config.properties");
		prop.load(data);
	}

	public static WebDriver getDriver() throws IOException {
		return WebDriverInstance.getDriver();
	}

	public String getUrl() throws IOException {
		url = prop.getProperty("url");
		return url;
	}

	public static String takeSnapShot(String name) throws IOException {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

		String destFile = System.getProperty("user.dir") + "\\target\\screenshots\\" + timestamp() + ".png";
		screenShotDestinationPath = destFile;

		try {
			FileUtils.copyFile(srcFile, new File(destFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return name;

	}

	public static String timestamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
	}

	public static String getScreenshotDestinationPath() {
		return screenShotDestinationPath;
	}

	public static void waitForElementInvisible(WebElement element, Duration timer) throws IOException {
		WebDriverWait wait = new WebDriverWait(getDriver(), timer);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public static String getValueFromJson(String filePath, String key) {
		try {
			String content = new String(Files.readAllBytes(Paths.get(filePath)));
			JSONObject jsonObject = new JSONObject(content);
			return findValue(jsonObject, key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String findValue(JSONObject jsonObject, String key) {
		for (String k : jsonObject.keySet()) {
			Object value = jsonObject.get(k);

			if (k.equals(key)) {
				return value.toString();
			}

			if (value instanceof JSONObject) {
				String result = findValue((JSONObject) value, key);
				if (result != null) {
					return result;
				}
			} else if (value instanceof JSONArray) {
				for (int i = 0; i < ((JSONArray) value).length(); i++) {
					Object element = ((JSONArray) value).get(i);
					if (element instanceof JSONObject) {
						String result = findValue((JSONObject) element, key);
						if (result != null) {
							return result;
						}
					}
				}
			}
		}
		return null;
	}

}
