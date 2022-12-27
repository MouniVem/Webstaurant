package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import util.TestUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class TestBase {

	public static WebDriver driver;
	public static Properties props;
	public static EventFiringWebDriver e_driver;
	//public static WebEventListener eventListener;
	public static ExtentTest logger;
	public static ExtentReports report;
	

	public TestBase() {
		try {
			props = new Properties();
			String currentDir = System.getProperty("user.dir");
			FileInputStream ip = new FileInputStream(
					currentDir+"\\src\\main\\java\\config\\config.properties");
			props.load(ip);
		}

		catch (FileNotFoundException ip) {
			ip.printStackTrace();
		}

		catch (IOException io) {
			io.printStackTrace();
		}

	}

	public static void initialization() {
		if (props.getProperty("browser").equals("chrome")) {
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (props.getProperty("browser").equals("edge")) {

		} else if (props.getProperty("browser").equals("firefox")) {

		}
		//e_driver = new EventFiringWebDriver(driver);
		// Now create object of WebEventListener handler to register it with EventFiringWebDriver
		//eventListener = new WebEventListener();
		//e_driver.register(eventListener);
		//Assign that to event listener driver to WebDriver.
		//driver = e_driver;

		driver.manage().window().maximize(); // maximize the windows
		driver.manage().deleteAllCookies(); // delete all the cookies
		driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtils.IMPLICIT_WAIT, TimeUnit.SECONDS);

		driver.get(props.getProperty("url"));

	}

	public void tearDown() {
		driver.quit();
	}

}
