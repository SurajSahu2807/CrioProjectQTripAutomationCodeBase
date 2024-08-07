package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventureDetailsPage {

    WebDriver driver;
	
	static private String num ;
	
	@FindBy(xpath = "//*[@id='duration-select']")
	WebElement DurationDropdown;
	
	@FindBy(xpath = "//*[@id= 'data' and @class='row']")
	WebElement List_Of_places;
	
	@FindBy(id = "category-select")
	WebElement CategoryDropdown;
	
	 @FindAll({@FindBy(xpath = 
	"//div[@class='row' and @id='data']//div[@class='col-6 col-lg-3 mb-4']//div[@class='category-banner']")})
	private List<WebElement> CategorySearchResults;
	 
	 @FindAll({@FindBy(xpath = 
	 "//div[@class='row' and @id='data']//div[@class='col-6 col-lg-3 mb-4']//div[@class='activity-card']//div[1]//div[2]//p")})
	 private List<WebElement> DurationSearchResults;
	 
	 @FindBy(xpath = "//div[@class='row' and @id='data']//div[@class='col-6 col-lg-3 mb-4']")
	 private List<WebElement> Total_Count_Of_Places;
	 
	 @FindBy(xpath = "//div[@onclick='clearDuration(event)']")
	 WebElement ClearBtn_Duration;
	 
	 @FindBy(xpath = "//div[@onclick='clearCategory(event)']")
	 WebElement ClearBtn_Category;

	 @FindBy(xpath = "//input[@name='name']")
	 WebElement AdventureNameFieldEle;
   
	 @FindBy(xpath = "//input[@name='date']")
	 WebElement DatePickerEle;
   
	 @FindBy(xpath = "//*[@id='myForm']/div[1]/div[2]/input")
	 WebElement NoOfPersonsFeildEle;
   
	 @FindBy(xpath = "//button[text()='Reserve']")
	 WebElement ReserveBtnEle;
   
	//  @FindBy(xpath = "//*[contains(text(),' Greetings! Reservation for this adventure is successful.')]")
	//  WebElement SuccesfulReserveGreetEle;

	@FindBy(xpath = "//*[@id='reserved-banner']")
	WebElement SuccesfulReserveGreetEle;

     public AdventureDetailsPage(WebDriver driver) {
		this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
	}
	
	public String Total_No_Of_Places() throws Exception {
		Thread.sleep(3000);
		num = String.valueOf(Total_Count_Of_Places.size());
		System.out.println("Total Number of records visible in adventures Page are :- "+num);
		return num;
	}

    public Boolean Select_No_Of_Hours(String Hours) {
		try {
			// DurationDropdown.click();
			SeleniumWrapper.click(DurationDropdown, driver);
			Select dropdown = new Select(DurationDropdown);
			dropdown.selectByVisibleText(Hours);
			System.out.println("Duration filter is applied");
			Thread.sleep(3000);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean Select_Category(String Category_Filter ) {
		try {
			// CategoryDropdown.click();
			SeleniumWrapper.click(CategoryDropdown, driver);
			Select Category = new Select(CategoryDropdown);
			Category.selectByVisibleText(Category_Filter);
			Thread.sleep(3000);
			System.out.println("Category filter is applied");
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int ToVerify_Appropriate_Adventures_Category_data_Is_displayed(String CategoryTitle) {
		int size;
		if(CategorySearchResults.size() == 0) {
			size=Total_Count_Of_Places.size();
			System.out.println("None Adventures are present for this category");
			return size;
		}else {
			size = Total_Count_Of_Places.size();
			for(WebElement e : CategorySearchResults) {
				String categoryname = e.getText();
				String[] arr = CategoryTitle.split(" ");
					if(categoryname.equalsIgnoreCase(arr[0])) {
					System.out.print(categoryname + " ");
					System.out.println("Appropriate data is displayed");
				}else {
					System.out.println(categoryname+ " " + "failed to display correct data"); 
				}
			}
			return size;
		}
}

public void ToVerify_Appropriate_Adventures_Duration_data_Is_displayed() {
	
	if(DurationSearchResults.size() == 0) {
		System.out.println("No adventures are present");
	}else {
		for(WebElement e : DurationSearchResults) {
			String[] arr = e.getText().split(" ");
			int num = Integer.parseInt(arr[0]);
			if(num>0) {
				System.out.print(num + " ");
				System.out.println("Appropriate Duration data is displayed");
			}else {
				System.out.println(num+ " " + "failed to display correct duration data"); 
			}
		}
	}
}

  public void PerformClearOperation() {
	  WebDriverWait wait = new WebDriverWait(driver,30);
		//   ClearBtn_Duration.click();
		SeleniumWrapper.click(ClearBtn_Duration, driver);
		  System.out.println("Cleared Duration filter");
		//   ClearBtn_Category.click();
		SeleniumWrapper.click(ClearBtn_Category, driver);
		  System.out.println("Cleared Category Filter");
		  wait.until(ExpectedConditions.visibilityOfAllElements(Total_Count_Of_Places));
  }
  

  public String _To_Verify_All_The_Records_Are_Displayed(String ln) {
	  if(String.valueOf(Total_Count_Of_Places.size()).equals(ln))
		  return "All the records are displayed";
	  else
		  return "Facing Issue in displaying all the records";
  }

  public Boolean ReserveTheAdventure(String Name,String Date,String Count) throws Exception {
	try {
		WebDriverWait wait = new WebDriverWait(driver,30);
		//System.out.println(AdventureNameFieldEle.isEnabled());
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = arguments[1]", AdventureNameFieldEle,Name);
		System.out.println(DatePickerEle.isEnabled()   );
		Thread.sleep(5000);
		// DatePickerEle.sendKeys(Date);
		SeleniumWrapper.SendKeys(DatePickerEle, Date);
		NoOfPersonsFeildEle.clear();
		// NoOfPersonsFeildEle.sendKeys(Count);
		SeleniumWrapper.SendKeys(NoOfPersonsFeildEle, Count);
		// ReserveBtnEle.click();
		SeleniumWrapper.click(ReserveBtnEle, driver);
		wait.until(ExpectedConditions.visibilityOf(SuccesfulReserveGreetEle));
		return true;
	}catch(Exception e) {
		e.printStackTrace();
		return false;
	}	
}

private Boolean status = false;


public Boolean ToVerifyReserveSuccessMsg() {
	status = SuccesfulReserveGreetEle.isDisplayed();
	if(status) {
		System.out.println("Verified adventure booking is successful");
		return true;
	}
	return false;
	}

	@FindBy(xpath = "//*[@id='reserved-banner']/a")
	WebElement ClickHereEle;

	@FindBy(xpath = "//tbody[@id='reservation-table']//tr[1]//td[8]//div//button")
	WebElement ToClickOnTheCancelBtnEle;


	@FindBy(xpath = "//tbody[@id='reservation-table']//tr[1]//th[1]")
	WebElement TransactionIdEle;



}