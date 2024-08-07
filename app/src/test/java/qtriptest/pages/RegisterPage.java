package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.sql.Timestamp;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    WebDriver driver;

    public String test_data_name;
	public String Password;
	private String url="https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	// By RegisterTextBox = By.xpath("//div[@class='form-floating mb-3'][1]//input[1]");	
	
	@FindBy(xpath = "//div[@class='form-floating mb-3'][1]//input[1]")
	WebElement RegisterTextBox;
	
	@FindBy(xpath = "//input[@placeholder='Type to create account password']")
	WebElement PasswordTextBox;
	
	@FindBy(xpath = "//input[@placeholder='Retype Password to Confirm']")
	WebElement ConfirmPasswordTextBox;
	
	@FindBy(xpath = "//button[text()='Register Now']")
	WebElement RegisterBtn;

	Boolean status = false;

    public RegisterPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    // public void NavigateToRegisterPage(){
    //     if(!driver.getCurrentUrl().equals(url)){
    //         driver.get(url);
    //         driver.manage().window().maximize();
    //     }
    // }

	public Boolean NavigateToRegisterPage(){
        // if(!driver.getCurrentUrl().equals(url)){
        //     driver.get(url);
        //     driver.manage().window().maximize();
        // }
		status = SeleniumWrapper.NavigatetoURL(driver, url);
		driver.manage().window().maximize();
		return status;
    }

    public Boolean UserRegistration(String username,String Password) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,30);
			test_data_name = String.valueOf(timestamp.getTime())+username;
			this.Password=Password;
			//RegisterTextBox.sendKeys(test_data_name);
			SeleniumWrapper.SendKeys(RegisterTextBox, test_data_name);
			//PasswordTextBox.sendKeys(Password);
			SeleniumWrapper.SendKeys(PasswordTextBox, Password);
			//ConfirmPasswordTextBox.sendKeys(Password);
			SeleniumWrapper.SendKeys(ConfirmPasswordTextBox, Password);
			// RegisterBtn.click();
			SeleniumWrapper.click(RegisterBtn, driver);
			wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/login"));
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}	
	}

    public Boolean ToVerifyRegistration() {
		if(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/login")) 
			return true;
		return false;     
	}

    public void Quite() {
		driver.quit();
	}
    
}
