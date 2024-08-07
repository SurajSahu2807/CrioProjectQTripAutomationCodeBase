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

public class testCase_04 {

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
        // final DesiredCapabilities capabilities = new DesiredCapabilities();
        // capabilities.setBrowserName(BrowserType.CHROME);
        // driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        DriverSingleton  singleton = DriverSingleton.getInstanceOfSingleTonBrowserClass();
        driver = singleton.getDriver();
        report = new ExtentReports(System.getProperty("user.dir")+"/OurExtentReport"+timestampString+".html");   
        //Starting a new test     
        test = report.startTest("Verify that Booking history can be viewed");
        // logStatus("driver", "Initializing driver", "Success");
      //  logStatus("driver", "Initializing driver", "Started");
       
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

    public static void logStatus2(String type, String functionality , String message, Boolean result2) {
        System.out.println(String.format("%s |  %s  |  %s   |   %s   | %b",
                String.valueOf(java.time.LocalDateTime.now()), type, functionality, message, result2));
    }


    @Test(description="Verify that Booking history can be viewed",priority=4,groups={"Reliability Flow"},enabled=true,dataProvider = "data-provider",dataProviderClass = DP.class)
    public static void TestCase04(String Username,String Password,String dataset1,String dataset2 ,String dataset3) throws Exception{
        String[] arr = new String[3];
        RegisterPage page = new RegisterPage(driver);
        result = page.NavigateToRegisterPage();
        testCase_04.ToCheck(result,"NavigateToRegisterPage method");
        Assertion Asserts = new Assertion();
        Asserts.assertEquals(RegistrationPageurl,driver.getCurrentUrl());
        logStatus("TestCase_04","Navigate to registration page","Successful");
        result = page.UserRegistration(Username,Password);
        testCase_04.ToCheck(result,"UserRegistration method");
        Last_Used_Username = page.test_data_name;
		Last_Used_Password=page.Password;
        Asserts.assertEquals(LoginPageUrl,driver.getCurrentUrl());
        logStatus("TestCase_04", "User_registration is :- ", "Successful");
        LoginPage logpage = new LoginPage(driver);
		result= logpage.User_Login(Last_Used_Username, Last_Used_Password);
        testCase_04.ToCheck(result,"User_Login method");
        Assert.assertTrue(result);
        logStatus("TestCase_04", "User_Login is :- ", "Successful");
        HomePage hpage = new HomePage(driver);
        String[] arr1 = dataset1.split(";");
        arr[0] = arr1[2].toString();
        result = hpage.Search_Your_Destination(arr1[0]);
        testCase_04.ToCheck(result,"Search_Your_Destination method");
        logStatus2("TestCase_04 Testing","To verify Searching destination is successfull or not ","Is destination search successful:- ", result);
        result = hpage.Verifcation_Of_No_Matches_Found_For_CityName(arr1[0]);
        testCase_04.ToCheck(result,"Verifcation_Of_No_Matches_Found_For_CityName method");
        logStatus2("TestCase_04 Testing","To verify wheather the city name is appreared or not ","Is city Name appeared :- ", result);
        AdventurePage AdvenPage = new AdventurePage(driver);
        result = AdvenPage.Total_No_Of_Places();
        testCase_04.ToCheck(result,"Total_No_Of_Places method");
        result = AdvenPage.SearchAdventures(arr1[1]);
        testCase_04.ToCheck(result,"SearchAdventures method");
        AdventureDetailsPage AdvenDetails = new AdventureDetailsPage(driver);
        result = AdvenDetails.ReserveTheAdventure(arr1[2],arr1[3],arr1[4]);
        testCase_04.ToCheck(result,"ReserveTheAdventure method");
        result = AdvenDetails.ToVerifyReserveSuccessMsg();
        testCase_04.ToCheck(result,"ToVerifyReserveSuccessMsg method");
        testCase_04.logStatus2("TestCase04 testing","To verify Adventure-Booking is successful or not "," adventure booking is successful : ", result); 
 
         // -----------------------------x-------------------------------------
        hpage.HomePageBtn();
        String[] arr2 = dataset2.split(";");
        arr[1] = arr2[2].toString();
        result = hpage.Search_Your_Destination(arr2[0]);
        testCase_04.ToCheck(result,"Search_Your_Destination method");
        logStatus2("TestCase_04 Testing","To verify Searching destination is successfull or not ","Is destination search successful:- ", result);
        result = hpage.Verifcation_Of_No_Matches_Found_For_CityName(arr2[0]);
        testCase_04.ToCheck(result,"Verifcation_Of_No_Matches_Found_For_CityName method");
        logStatus2("TestCase_04 Testing","To verify wheather the city name is appreared or not ","Is city Name appeared :- ", result);        AdvenPage.Total_No_Of_Places();
        result = AdvenPage.Total_No_Of_Places();
        testCase_04.ToCheck(result,"Total_No_Of_Places method");
        AdvenPage.SearchAdventures(arr2[1]);
        testCase_04.ToCheck(result,"SearchAdventures method");
        AdvenDetails.ReserveTheAdventure(arr2[2],arr2[3],arr2[4]);
        testCase_04.ToCheck(result,"ReserveTheAdventure method");
        result = AdvenDetails.ToVerifyReserveSuccessMsg();
        testCase_04.ToCheck(result,"ToVerifyReserveSuccessMsg method");
        testCase_04.logStatus2("TestCase04 testing","To verify Adventure-Booking is successful or not "," adventure booking is successful : ", result); 
        //-------------------------------x_-------------------------
        hpage.HomePageBtn();
        Thread.sleep(3000);
        String[] arr3 = dataset3.split(";");
        arr[2] = arr3[2].toString();
        result = hpage.Search_Your_Destination(arr3[0]);
        testCase_04.ToCheck(result,"Search_Your_Destination method");
        logStatus2("TestCase_04 Testing","To verify Searching destination is successfull or not ","Is destination search successful:- ", result);
        result = hpage.Verifcation_Of_No_Matches_Found_For_CityName(arr3[0]);
        testCase_04.ToCheck(result,"Verifcation_Of_No_Matches_Found_For_CityName method");
        logStatus2("TestCase_04 Testing","To verify wheather the city name is appreared or not ","Is city Name appeared :- ", result);
        result = AdvenPage.Total_No_Of_Places();
        testCase_04.ToCheck(result,"ReserveTheAdventure method");
        result = AdvenPage.SearchAdventures(arr3[1]);
        testCase_04.ToCheck(result,"ReserveTheAdventure method");
        result =  AdvenDetails.ReserveTheAdventure(arr3[2],arr3[3],arr3[4]);
        testCase_04.ToCheck(result,"ReserveTheAdventure method");
        result = AdvenDetails.ToVerifyReserveSuccessMsg();
        testCase_04.ToCheck(result,"ToVerifyReserveSuccessMsg method");
        testCase_04.logStatus2("TestCase04 testing","To verify Adventure-Booking is successful or not "," adventure booking is successful : ", result); 
        HistoryPage HistPage = new HistoryPage(driver);
		result = HistPage.ClickOnTheHistoryPage();
        testCase_04.ToCheck(result,"ClickOnTheHistoryPage method");
        Thread.sleep(5000);
        result = HistPage.ToVerifyAlltheBookingAreDisplayed(arr);
        testCase_04.ToCheck(result,"ToVerifyAlltheBookingAreDisplayed method");
        testCase_04.logStatus2("TestCase04 testing","ToVerifyAlltheBookingAreDisplayed method is successful or not ","AlltheBookingAreDisplayed method is successful : ", result); 
		
    }           

    @AfterClass(enabled = true)
    public static void quitDriver() throws MalformedURLException {
        report.endTest(test);
        report.flush();
        driver.close();
        driver.quit();
        logStatus("driver", "Quitting driver", "Success");
    }

    
}
