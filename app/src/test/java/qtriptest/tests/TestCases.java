package qtriptest.tests;

import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCases {
    
    static RemoteWebDriver driver;
    static Boolean result = false;

    // Method to help us log our Unit Tests
    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s |  %s  |  %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    // Iinitialize webdriver for our Unit Tests
@BeforeClass(alwaysRun = true, enabled = false)
public static void createDriver() throws MalformedURLException {
    logStatus("driver", "Initializing driver", "Started");
    final DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setBrowserName(BrowserType.CHROME);
    driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
    logStatus("driver", "Initializing driver", "Success");
}

// Type here for Unit test

@Test(priority=1,enabled = false)
 public static void User_Registration_Functionality(){
    try{
        RegisterPage r1 = new RegisterPage(driver);
        r1.NavigateToRegisterPage();
        logStatus("Unit Test","user registration","Started");
        result = r1.UserRegistration("Suraj","Pass@1234");
        if(result)
            logStatus("Unit test", "user registration", "Completed");
        else
             logStatus("Unit test", "user registration", "Failed");
        //r1.ToVerifyRegistration();
    }catch(Exception e){
        e.printStackTrace();
        logStatus("Unit Test","iser registration fails", "Failed");
    }
 }


 @Test(priority=2,enabled=false)
 public static void Verify_User_Registration_functionality(){
    try{
        RegisterPage r1 = new RegisterPage(driver);
        logStatus("Unit Test","Verify_User_Registration","Started");
        result = r1.ToVerifyRegistration();
        if(result)
        logStatus("Unit test", "Verify_User_Registration", "Completed");
    else
         logStatus("Unit test", "Verify_User_Registration", "Failed");
    }catch(Exception e){
        e.printStackTrace();
        logStatus("Unit Test","iser registration fails", "Failed");
    }
 }


// Quit webdriver after Unit Tests
@AfterClass(enabled = false)
public static void quitDriver() throws MalformedURLException {
    driver.close();
    driver.quit();
    logStatus("driver", "Quitting driver", "Success");
}


}
