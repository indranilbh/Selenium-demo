package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverInstance {

	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static WebDriver getDriver() {
		if (driver.get() == null) {
			try {
				driver.set(createDriver());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return driver.get();
	}

	public static WebDriver createDriver() throws IOException {
		WebDriver driver = null;

		Properties prop = new Properties();
		FileInputStream data = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\resources\\config.properties");
		prop.load(data);

		if (prop.getProperty("browser").equals("chrome")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			if (prop.getProperty("mobile").equals("yes")) {
				Map<String, Object> deviceMetrics = new HashMap<>();
		        deviceMetrics.put("width", 375);
		        deviceMetrics.put("height", 812);
		        deviceMetrics.put("pixelRatio", 3.0);
		        deviceMetrics.put("mobile", true);
		        Map<String, Object> mobileEmulation = new HashMap<>();
		        mobileEmulation.put("deviceMetrics", deviceMetrics);
		        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
			}
			
		    driver = new ChromeDriver(chromeOptions);
		    if(!prop.getProperty("mobile").equals("yes"))
		    	driver.manage().window().maximize();
		    
		 } else if (prop.getProperty("browser").equals("edge")) {
			 EdgeOptions edgeOptions =new EdgeOptions();
			 driver = new EdgeDriver(edgeOptions);
			 driver.manage().window().maximize();
		} else {
			
		}

		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}

	public static void cleanupDriver() {
		driver.get().quit();
		driver.remove();
	}

}
