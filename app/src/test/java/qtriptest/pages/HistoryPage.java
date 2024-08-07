
package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HistoryPage {

    WebDriver driver;

    @FindBy(xpath = "//*[@id='reserved-banner']/a")
	WebElement ClickHereEle;

    @FindBy(xpath = "//tbody[@id='reservation-table']//tr[1]//td[8]//div//button")
	WebElement ToClickOnTheCancelBtnEle;

    @FindBy(xpath = "//tbody[@id='reservation-table']//tr[1]//th[1]")
	WebElement TransactionIdEle;

    public HistoryPage(WebDriver driver) {
		this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
	}

	private String TransactionId;
	
	public Boolean ToGetTransactionId() {
		try{
			TransactionId = TransactionIdEle.getText();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
    public Boolean toCheckTransacIdIsRemoved() throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver,30);// Adjust the timeout as needed
		try {
		    wait.until(ExpectedConditions.visibilityOf(TransactionIdEle));
			return false; // Element is still displayed, transaction ID is not removed
		} catch (org.openqa.selenium.TimeoutException e) {
			return true; // Element is not found, transaction ID is removed
		}
	}

    public Boolean ToClickOnTheCancelBtn() throws InterruptedException {
		try{
			Thread.sleep(3000);
			// ToClickOnTheCancelBtnEle.click();
			SeleniumWrapper.click(ToClickOnTheCancelBtnEle, driver);
			Thread.sleep(3000);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

    public Boolean ClickOnTheHistoryPage() {
		try{
			SeleniumWrapper.click(ClickHereEle, driver);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		// ClickHereEle.click();
	}

	@FindAll({@FindBy(xpath = 
		"//tbody[@id='reservation-table']//tr//td[1]")})
		private List<WebElement> CategorySearchResults;

	public Boolean ToVerifyAlltheBookingAreDisplayed(String[] arr){
		try{
			int num = 0;
			for(WebElement e : CategorySearchResults){
				String BookingName =  e.getText();
				for(String f : arr){
					if(BookingName.equals(f)){
						// System.out.println(f + " Booking Name is present");
						num++;
					}
				}
			}
			if(num == 3){
				System.out.println("All three Bookings are present");
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	


}