package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class testCase_01 {

    static RemoteWebDriver driver;
    static Boolean result = false;
    static String Last_Used_Username;
	static String Last_Used_Password;
    public static String RegistrationPageurl = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";
    public static String LoginPageUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/login";
    static ExtentTest test;
    static ExtentReports report;
    static Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    static String timestampString = String.valueOf(timestamp.getTime());

        // Method to help us log our Unit Tests
        public static void logStatus(String type, String message, String status) {
            System.out.println(String.format("%s |  %s  |  %s | %s",
                    String.valueOf(java.time.LocalDateTime.now()), type, message, status));
        }

        public static void ToCheck(Boolean status,String Message) throws IOException{
            if(status){
                test.log(LogStatus.PASS, Message + "Passed");
            }else{
                test.log(LogStatus.FAIL, Message + "Failed");
                test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+ "Test Failed");
            }
        } 

        
            public static String capture(WebDriver driver) throws IOException{
                //TODO: Add all the components here
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); 
                File Dest = new File(System.getProperty("user.dir")+"/QTRIPQAImages/" + System.currentTimeMillis()+ ".png");
                String errflpath = Dest.getAbsolutePath();
                FileUtils.copyFile(scrFile, Dest);
                return errflpath;
            }

    @BeforeClass(alwaysRun = true, enabled = true)
    public static void createDriver() throws MalformedURLException {
        logStatus("driver", "Initializing driver", "Started");
        DriverSingleton singleton = DriverSingleton.getInstanceOfSingleTonBrowserClass();
        driver = singleton.getDriver();
        logStatus("driver", "Initializing driver", "Success");
        report = new ExtentReports(System.getProperty("user.dir")+"/OurExtentReport"+timestampString+".html");        
        test = report.startTest("Verify user registration - login - logout");
        report.loadConfig(new File(System.getProperty("user.dir")+"/extent_customization_configs.xml"));

    }

    @Test(description="Verify user registration - login - logout",enabled=true,priority=1,groups={"Login Flow"},dataProvider = "data-provider",dataProviderClass = DP.class)
    public static void TestCase01(String Username,String Password) throws IOException{
        RegisterPage page = new RegisterPage(driver);
        result = page.NavigateToRegisterPage();
        testCase_01.ToCheck(result, "Navigate to Url");
        Assertion Asserts = new Assertion();
        Asserts.assertEquals(RegistrationPageurl,driver.getCurrentUrl());
        logStatus("TestCase_01","Navigate to registration page","Successful");
        result = page.UserRegistration(Username,Password);
        testCase_01.ToCheck(result,"User registration");
        Last_Used_Username = page.test_data_name;
		Last_Used_Password=page.Password;
        Asserts.assertEquals(LoginPageUrl,driver.getCurrentUrl());
        logStatus("TestCase_01", "User_registration is :- ", "Successful");
        LoginPage logpage = new LoginPage(driver);
		result= logpage.User_Login(Last_Used_Username, Last_Used_Password);
        testCase_01.ToCheck(result, "User Login");
        Assert.assertTrue(result);
        logStatus("TestCase_01", "User_Login is :- ", "Successful");
        Assert.assertTrue(logpage.ToVerifyLogOut(),"User_Login is Failed");
    }



    @AfterClass(enabled = false)
    public static void quitDriver() throws MalformedURLException {
        report.endTest(test);
        report.flush();
        driver.close();
        driver.quit();
        logStatus("driver", "Quitting driver", "Success");
    }


}
