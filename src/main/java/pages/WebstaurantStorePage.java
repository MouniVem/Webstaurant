package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import base.TestBase;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class WebstaurantStorePage extends TestBase{
	
	//Load PageFactory(OR - Object Repository)
	@FindBy(name="searchval")
	WebElement searchtext;
	
	@FindBy(xpath="//*[@value='Search']")
	WebElement searchbutton;
	
	@FindBy(xpath="//a[contains(@aria-label,'WebstaurantStore')]")
	WebElement pageHeader;
	
	@FindAll({@FindBy(xpath="//*[@id='ProductBoxContainer']")})
	public List<WebElement> searchProducts;

	@FindAll({@FindBy(xpath="//*[@data-testid='itemDescription']")})
	public List<WebElement> searchProductsDesc;

	@FindBy(xpath="//a[contains(@aria-label,'last page')]")
	WebElement pagination_count;

	@FindBy(xpath="//span[contains(text(),'Cart') and contains(@class,'hidden xsl:inline')]")
	WebElement viewcart;

	@FindBy(xpath="//button[contains(text(),'Empty Cart')]")
	WebElement emptycart;

	@FindBy(xpath="(//*[@data-testid='itemDescription'])[last()]/../../div[@class='add-to-cart']/form/div[@class='btn-container']/div/input[2]")
	WebElement addtocart_lastitem;

	@FindBy(xpath="(//*[@data-testid='itemDescription'])[last()-1]/../../div[@class='add-to-cart']/form/div[@class='btn-container']/div/input[2]")
	WebElement addtocart_lastbutoneitem;

	@FindAll({@FindBy(xpath="(//*[@data-testid='itemDescription'])[last()]/../../div[@class='add-to-cart']/form/div[@class='btn-container']/div/input")})
	public List<WebElement> FE_addtocart_lastitem;

	@FindAll({@FindBy(xpath="(//*[@data-testid='itemDescription'])[last()-1]/../../div[@class='add-to-cart']/form/div[@class='btn-container']/div/input")})
	public List<WebElement> FE_addtocart_lastbutoneitem;

	@FindBy(xpath="//button[contains(text(),'Empty') and contains(@class,'bg-origin-box-border')]")
	WebElement emtpycart_popup;

	//Initializing PageFactory
	public WebstaurantStorePage() {
		PageFactory.initElements(driver, this); //this --> points to current class object.
	}

	public Boolean validatePageHeader() {
		return pageHeader.isDisplayed();
	}

	public void searchProductbyName(String name) {
		searchtext.sendKeys(name);
		searchbutton.click();
	}

	public String verifySearchResults() {
		searchProducts.size();
		int productCount = 0;
		int counter=0;
		String a="";
		int pgcount = Integer.parseInt(pagination_count.getText());
		//Count no of pagination link
		new WebDriverWait(
				driver, 20).until(
				ExpectedConditions.presenceOfElementLocated(
						By.xpath("//a[contains(@aria-label,'last page')]")));
		//Iterate through list
		for (int i = 1; i<=pgcount; i++) {
			// Click on each page
			driver.findElement(By.xpath("//li[contains(@class,'inline-block leading')]/a[contains(text(),'"+i+"')]")).click();
			//Add List size to existing pages product count
			productCount = productCount + searchProducts.size();
			// loop through all products displayed in one page and search for "table" string
			// Click on last product
			for (int p = 0; p < searchProducts.size(); p++) {
				a = searchProductsDesc.get(p).getText();
				if(!(a.contains(" Table"))){
					counter++;
				}
			}
		}
		return "Number of Products which do not have search string -- Table: "+counter;
	}

	public void clickLastProduct(){
		//find last products and click if Add to cart button is present
		if(FE_addtocart_lastitem.size()>0){
			addtocart_lastitem.click();
		}else if(FE_addtocart_lastbutoneitem.size()>0){
			addtocart_lastbutoneitem.click();
			System.out.println("Add to cart is not present for last item, hence added the last but one item to cart");
		}else{
			System.out.println("Add to cart is not present for last item and last but one item. None of the items are added to cart");
		}
	}
	

	public void ViewCart(){
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("hidden xsl:inline")));

		viewcart.click();

		if(emptycart.isDisplayed()){
			emptycart.click();
			emtpycart_popup.click();

		}else {
			System.out.println("emptycart is not displayed");
		}
	}

	public void EmptyCart(){
		if(emptycart.isDisplayed()){
			emptycart.click();
			emtpycart_popup.click();

		}else {
			System.out.println("emptycart is not displayed");
		}
	}

}
