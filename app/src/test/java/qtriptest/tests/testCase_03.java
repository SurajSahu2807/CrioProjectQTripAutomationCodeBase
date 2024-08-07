package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
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

public class testCase_03 {

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


    @BeforeClass(alwaysRun = true, enabled = true)
    public static void createDriver() throws MalformedURLException {
        // logStatus("driver", "Initializing driver", "Started");
        DriverSingleton  singleton = DriverSingleton.getInstanceOfSingleTonBrowserClass();
        driver = singleton.getDriver();
        report = new ExtentReports(System.getProperty("user.dir")+"/OurExtentReport"+timestampString+".html");   
         //Starting a new test     
        test = report.startTest("Verify that adventure booking and cancellation works fine");
        // logStatus("driver", "Initializing driver", "Success");
        // final DesiredCapabilities capabilities = new DesiredCapabilities();
        // capabilities.setBrowserName(BrowserType.CHROME);
        // driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        // logStatus("driver", "Initializing driver", "Success");
    }

    public static String capture(WebDriver driver) throws IOException{
        //TODO: Add all the components here
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); 
        File Dest = new File(System.getProperty("user.dir")+"/QTRIPQAImages/" + System.currentTimeMillis()+ ".png");
        String errflpath = Dest.getAbsolutePath();
        FileUtils.copyFile(scrFile, Dest);
        return errflpath;
    }

    public static void ToCheck(Boolean status,String Message) throws IOException{
        if(status){
            test.log(LogStatus.PASS, Message + "Passed");
        }else{
            test.log(LogStatus.FAIL, Message + "Failed");
            test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+ "Test Failed");
        }
    }

    

    @Test(description="Verify that adventure booking and cancellation works fine",priority=3,groups={"Booking and Cancellation Flow"},enabled=true,dataProvider = "data-provider",dataProviderClass = DP.class)
    public static void TestCase03(String Username,String Password,String SearchCity,String AdventureName,String GuestName,String Date,String count) throws Exception{
        RegisterPage page = new RegisterPage(driver);
        result = page.NavigateToRegisterPage();
        testCase_03.ToCheck(result, "Navigate to register page method");
        Assertion Asserts = new Assertion();
        Asserts.assertEquals(RegistrationPageurl,driver.getCurrentUrl());
        logStatus("TestCase_03","Navigate to registration page","Successful");
        result = page.UserRegistration(Username,Password);
        testCase_03.ToCheck(result, "User Registration method");
        Last_Used_Username = page.test_data_name;
		Last_Used_Password=page.Password;
        Asserts.assertEquals(LoginPageUrl,driver.getCurrentUrl());
        logStatus("TestCase_03", "User_registration is :- ", "Successful");
        LoginPage logpage = new LoginPage(driver);
		result= logpage.User_Login(Last_Used_Username, Last_Used_Password);
        testCase_03.ToCheck(result, "User login method");
        Assert.assertTrue(result);
        logStatus("TestCase_03", "User_Login is :- ", "Successful");
        //Assert.assertTrue(logpage.ToVerifyLogOut(),"User_Login is Failed");
        HomePage hpage = new HomePage(driver);
        result = hpage.Search_Your_Destination(SearchCity);
        testCase_03.ToCheck(result, "Search_Your_Destination method");
        result = hpage.Verifcation_Of_No_Matches_Found_For_CityName(SearchCity);
        testCase_03.ToCheck(result, "Verifcation_Of_No_Matches_Found_For_CityName method");
        logStatus2("TestCase_03 Testing","To verify wheather the city name is appreared or not ","Is city Name appeared :- ", result);
        AdventurePage AdvenPage = new AdventurePage(driver);
		result = AdvenPage.Total_No_Of_Places();
        testCase_03.ToCheck(result,"Total_No_Of_Places method");
        result = AdvenPage.SearchAdventures(AdventureName);
        testCase_03.ToCheck(result,"Search_Adventures method");
        AdventureDetailsPage AdvenDetails = new AdventureDetailsPage(driver);
		result = AdvenDetails.ReserveTheAdventure(GuestName,Date,count);
        testCase_03.ToCheck(result,"Reserve_The_Adventure method" );
        result = AdvenDetails.ToVerifyReserveSuccessMsg();
        testCase_03.ToCheck(result,"ToVerifyReserveSuccessMsg method" );
        testCase_03.logStatus2("TestCase03 testing","To verify Adventure-Booking is successful or not "," adventure booking is successful : ", result); 
        HistoryPage HistPage = new HistoryPage(driver);
		result = HistPage.ClickOnTheHistoryPage();
        testCase_03.ToCheck(result,"ClickOnTheHistoryPage method" );
        result = HistPage.ToGetTransactionId();
        testCase_03.ToCheck(result,"ToGetTransactionId method" );
		result = HistPage.ToClickOnTheCancelBtn();
        testCase_03.ToCheck(result,"ToClickOnTheCancelBtn method" );
        result=HistPage.toCheckTransacIdIsRemoved();
        testCase_03.ToCheck(result,"toCheckTransacIdIsRemoved method" );
		testCase_03.logStatus2("TestCase03 testing","To verify Transaction Id is removed or not","Transaction-id is not displayed : ", result);
    }


    public static void logStatus2(String type, String functionality , String message, Boolean result2) {
        System.out.println(String.format("%s |  %s  |  %s   |   %s   | %b",
                String.valueOf(java.time.LocalDateTime.now()), type, functionality, message, result2));
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
