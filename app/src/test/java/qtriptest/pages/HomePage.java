package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;
	private String CityName;

	@FindBy(xpath = "//h5[text()='No City found']")
	private WebElement NoCityMsgs;
	
	@FindBy(id = "autocomplete")
	WebElement SearchTextBox;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
	}
	

	@FindBy(xpath = "//a[text()='Home']")
	WebElement HomeBtn;

	public void HomePageBtn() throws InterruptedException{
		Thread.sleep(5000);
		// HomeBtn.click();
		SeleniumWrapper.click(HomeBtn, driver);
		Thread.sleep(5000);
	}

	public Boolean Search_Your_Destination(String CityName) {
		try {
			this.CityName=CityName;
			WebDriverWait wait = new WebDriverWait(driver,30);
			SearchTextBox.clear();
			// SearchTextBox.click();
			SeleniumWrapper.click(SearchTextBox, driver);
			// SearchTextBox.sendKeys(CityName);
			SeleniumWrapper.SendKeys(SearchTextBox, CityName);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	public Boolean Verifcation_Of_No_Matches_Found_For_CityName(String CityName) throws Exception {
		try {
			Thread.sleep(3000);
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()=' "+ CityName +" ']")));
			driver.findElement(By.xpath("//li[text()=' "+ CityName +" ']")).click();
			return true;
		}catch(org.openqa.selenium.TimeoutException e) {
			if(NoCityMsgs.isDisplayed()){
				System.out.println("The Message text which is appeared :- " + NoCityMsgs.getText());
				System.out.println("No City found Message is displayed");
			}	
			return false;
		}
	}
	

}
