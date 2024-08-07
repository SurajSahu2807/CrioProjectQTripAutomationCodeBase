package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.HomePage;
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

public class testCase_02 {
    

        static RemoteWebDriver driver;
        static Boolean result = false;
        static String HomePageUrl = "https://qtripdynamic-qa-frontend.vercel.app/";

            // Method to help us log our Unit Tests
            public static void logStatus1(String type, String message, String status) {
                System.out.println(String.format("%s |  %s  |  %s | %s",
                        String.valueOf(java.time.LocalDateTime.now()), type, message, status));
            }
            public static void logStatus2(String type, String functionality , String message, Boolean result2) {
                System.out.println(String.format("%s |  %s  |  %s   |   %s   | %b",
                        String.valueOf(java.time.LocalDateTime.now()), type, functionality, message, result2));
            }


            public static void ToCheck(Boolean status,String Message) throws IOException{
                if(status){
                    test.log(LogStatus.PASS, Message + "Passed");
                }else{
                    test.log(LogStatus.FAIL, Message + "Failed");
                    test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+ "Test Failed");
                }
            }

            
        static ExtentTest test;
        static ExtentReports report;
        static Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        static String timestampString = String.valueOf(timestamp.getTime());
    
        @BeforeClass(alwaysRun = false, enabled = true)
        public static void createDriver() throws MalformedURLException {
            // logStatus1("driver", "Initializing driver", "Started");
            // final DesiredCapabilities capabilities = new DesiredCapabilities();
            // capabilities.setBrowserName(BrowserType.CHROME);
            // driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
            DriverSingleton singleton = DriverSingleton.getInstanceOfSingleTonBrowserClass();
            driver = singleton.getDriver();
            // Creating an instance of extentreport 
            report = new ExtentReports(System.getProperty("user.dir")+"/OurExtentReport"+timestampString+".html");   
            //Starting a new test     
            test = report.startTest("Verify that Search and filters are working fine");
            // logStatus1("driver", "Initializing driver", "Success");
        }


            public static String capture(WebDriver driver) throws IOException{
                //TODO: Add all the components here
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); 
                File Dest = new File(System.getProperty("user.dir")+"/QTRIPQAImages/" + System.currentTimeMillis()+ ".png");
                String errflpath = Dest.getAbsolutePath();
                FileUtils.copyFile(scrFile, Dest);
                return errflpath;
            }
   
        //     public static void TestCase02() throws Exception{
            @Test(description="Verify that Search and filters are working fine",priority=2,groups={"Search and Filter Flow"},enabled=true,dataProvider = "data-provider",dataProviderClass = DP.class)
            public static void TestCase02(String CityName,String Category_Filter,String Duration_Filters,String ExpectedFilteredResults,String ExpectedUnFilteredResults ) throws Exception{
            driver.get(HomePageUrl);
            driver.manage().window().maximize();
            Thread.sleep(2000);
            HomePage hpage = new HomePage(driver);
            // hpage.Search_Your_Destination("nagpur");
            result = hpage.Search_Your_Destination("nagpur");
            testCase_02.ToCheck(result,"Search Your destination method 1");
            result = hpage.Verifcation_Of_No_Matches_Found_For_CityName("nagpur");
            testCase_02.ToCheck(result,"To verify No_Matches_Found Popup method 1");
            logStatus2("TestCase_02 Testing","To verify wheather the city name is appreared or not ","Is city Name appeared :- ", result);
            result = hpage.Search_Your_Destination(CityName); 		
            testCase_02.ToCheck(result,"Search Your destination method 2");
		    result = hpage.Verifcation_Of_No_Matches_Found_For_CityName(CityName);
            testCase_02.ToCheck(result,"To verify No_Matches_Found Popup method 2");
            logStatus2("TestCase_02 Testing","To verify wheather the city name is appreared or not ","Is city Name appeared :- ", result);
            AdventureDetailsPage AdvenPage = new AdventureDetailsPage(driver);
		    String TotalRecords_PresentWithoutFilters = AdvenPage.Total_No_Of_Places();
            Assert.assertEquals(TotalRecords_PresentWithoutFilters, ExpectedUnFilteredResults, "Total Number of places present in adventures page is not matching with the expected number");
            // AdvenPage.Select_No_Of_Hours("6-12 Hours");
            result = AdvenPage.Select_No_Of_Hours(Duration_Filters);
            testCase_02.ToCheck(result,"Select_No_Of_Hours method");
            AdvenPage.ToVerify_Appropriate_Adventures_Duration_data_Is_displayed();
            // AdvenPage.Select_Category("Cycling Routes");
            result = AdvenPage.Select_Category(Category_Filter);
            testCase_02.ToCheck(result,"Select Category method");
            String TotalRecords_PresentWithFilters = AdvenPage.Total_No_Of_Places();
            Assert.assertEquals(TotalRecords_PresentWithFilters, ExpectedFilteredResults, "Total Number of places present in adventures page after applying category filter  is not matching with the expected number");
            AdvenPage.ToVerify_Appropriate_Adventures_Category_data_Is_displayed(Category_Filter);
            AdvenPage.PerformClearOperation();
            System.out.println(AdvenPage._To_Verify_All_The_Records_Are_Displayed(TotalRecords_PresentWithoutFilters));
            
        }


        @AfterClass(enabled = false)
        public static void quitDriver() throws MalformedURLException {
            report.endTest(test);
            report.flush();
            driver.close();
            driver.quit();
            logStatus1("driver", "Quitting driver", "Success");
        }


}
