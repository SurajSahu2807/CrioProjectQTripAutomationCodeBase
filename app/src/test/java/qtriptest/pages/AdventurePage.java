package qtriptest.pages;


import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventurePage {

    WebDriver driver;
	
	static private int num ;

	
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

	 @FindBy(id = "search-adventures")
	 WebElement SearchAdventuresTextBoxEle;

     public AdventurePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
     }

	 public Boolean SearchAdventures(String AdventureName) throws InterruptedException {
		try{
			SearchAdventuresTextBoxEle.sendKeys(AdventureName);
		Thread.sleep(5000);
		// WebDriverWait wait = new WebDriverWait(driver,30);
		// wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='col-6 col-lg-3 mb-4']//div[2]//div[1]//div[1]//h5")));
		//WebElement adventurePlace = driver.findElement(By.xpath("//h5[text()='"+AdventureName+"']"));
		List<WebElement> Eles = driver.findElements(By.xpath("//div[@class='col-6 col-lg-3 mb-4']//div[2]//div[1]//div[1]//h5"));
		for(WebElement e : Eles) {
			if(e.getText().equals(AdventureName)) {
				Actions actions = new Actions(driver);
				actions.moveToElement(e).click().perform();
				break;
			}
		}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		// WebElement adventurePlace = driver.findElement(By.xpath("//h5[text()='"+AdventureName+"']"));
		// Thread.sleep(3000);
		// WebDriverWait wait = new WebDriverWait(driver,30);
		// wait.until(ExpectedConditions.visibilityOf(adventurePlace));
		// //adventurePlace.click();
		// Actions actions = new Actions(driver);
		// actions.moveToElement(adventurePlace).click().perform();
	}

	 public Boolean Total_No_Of_Places() throws Exception {
		try{
			Thread.sleep(3000);
			num =  Total_Count_Of_Places.size();
			System.out.println("Total Number of records visible is :- "+num);
		return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	public Boolean Select_No_Of_Hours() {
		try {
			DurationDropdown.click();
			Select dropdown = new Select(DurationDropdown);
			dropdown.selectByIndex(4);
			Thread.sleep(3000);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean Select_Category() {
		try {
			CategoryDropdown.click();
			Select Category = new Select(CategoryDropdown);
			Category.selectByIndex(3);
			Thread.sleep(3000);
			return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public void ToVerify_Appropriate_Adventures_Category_data_Is_displayed(String CategoryTitle) {
		
			if(CategorySearchResults.size() == 0) {
				System.out.println("None Adventures are present for this category");
			}else {
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
	  
	  
	  public String _To_Verify_All_The_Records_Are_Displayed() {
		  if(Total_Count_Of_Places.size() ==  num)
			  return "All the records are displayed";
		  else
			  return "Facing Issue in displaying all the records";
	  }
     

	}