package tests;

import com.relevantcodes.extentreports.ExtentReports;
import pages.WebstaurantStorePage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;
import base.TestBase;

public class WebstaurantStorePageTest extends TestBase{
	
	WebstaurantStorePage webstaurantStorePage;

	//Initializing PageFactory
	public WebstaurantStorePageTest() {
		super();   //Call the Constructor of the Super class - TestBase
	}
	
	@BeforeMethod
	public void setUp() {
		initialization();
		webstaurantStorePage = new WebstaurantStorePage();
		report = new ExtentReports(System.getProperty("user.dir")+"\\report\\ExtentReportResults.html");
		logger = report.startTest("Webstaurant");
	}

	@Test(priority=1)
	public void verifyWebstaurantProduct() throws InterruptedException {
		Assert.assertTrue(webstaurantStorePage.validatePageHeader());
		logger.log(LogStatus.INFO, "Open Webstaurantstore page");
		webstaurantStorePage.searchProductbyName("stainless work table");
		logger.log(LogStatus.INFO, "Search for product by name");
		String strResult = webstaurantStorePage.verifySearchResults();
		logger.log(LogStatus.INFO, "Verify search results and search string for each Product");
		logger.log(LogStatus.INFO, strResult);
		webstaurantStorePage.clickLastProduct();
		logger.log(LogStatus.INFO, "Click on last product if add to cart button is present");
		webstaurantStorePage.ViewCart();
		logger.log(LogStatus.INFO, "View Cart - Product present in cart");
		webstaurantStorePage.EmptyCart();
		logger.log(LogStatus.INFO, "Empty Cart - Product removed from cart");
	}

	@AfterMethod
	public void tearDown() {
		report.endTest(logger);
		report.flush();
		super.tearDown();
	}

}
