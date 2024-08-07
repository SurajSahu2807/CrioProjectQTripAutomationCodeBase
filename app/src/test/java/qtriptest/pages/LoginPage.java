package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage{

WebDriver driver;
	
	@FindBy(xpath = "//input[@placeholder='name@example.com']")
	WebElement EmailTextBox;
	
	@FindBy(xpath = "//input[@placeholder='Password']")
	WebElement PasswordTextBox;
	
	@FindBy(xpath = "//button[text()='Login to QTrip']")
	WebElement LoginToQtripBtn;
	
	@FindBy(xpath = "//div[text()='Logout']")
	WebElement LogOutBtn;
	
	@FindBy(xpath = "//h1[text()='Welcome to QTrip']")
	WebElement WelcomeLine;
	
	@FindBy(xpath = "//a[text()='Register']")
	WebElement RegisterBtn;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
	}

    public Boolean User_Login(String Username , String Password) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,30);
			// EmailTextBox.sendKeys(Username);
			SeleniumWrapper.SendKeys(EmailTextBox, Username);
			// PasswordTextBox.sendKeys(Password);
			SeleniumWrapper.SendKeys(PasswordTextBox, Password);
			// LoginToQtripBtn.click();
			SeleniumWrapper.click(LoginToQtripBtn, driver);
			wait.until(ExpectedConditions.visibilityOf(LogOutBtn));
			Thread.sleep(5000);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean ToVerifyLogin() {
		try {
			WebDriverWait wait = new WebDriverWait(driver,30);
			ExpectedCondition Expect1 = ExpectedConditions.visibilityOf(WelcomeLine);
			ExpectedCondition Expect2 = ExpectedConditions.visibilityOf(LogOutBtn);
			wait.until(ExpectedConditions.and(Expect1,Expect2));
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean ToVerifyLogOut() {
		try {
			LogOutBtn.click();
			WebDriverWait wait = new WebDriverWait(driver,30);
			ExpectedCondition Expect1 = ExpectedConditions.visibilityOf(RegisterBtn);
			ExpectedCondition Expect2 = ExpectedConditions.visibilityOf(WelcomeLine);
			wait.until(ExpectedConditions.and(Expect1,Expect2));
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}

}
