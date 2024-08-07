package qtriptest;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton{

    static  RemoteWebDriver driver;
    private static DriverSingleton instanceOfSingleTon;


    // This method is used to create an instance of the RemoteWebDriver
    private DriverSingleton() throws MalformedURLException{
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
    }
    

    public static DriverSingleton getInstanceOfSingleTonBrowserClass() throws MalformedURLException{
        if(driver == null){
            instanceOfSingleTon = new DriverSingleton();
        }
        return instanceOfSingleTon;
    }

    public RemoteWebDriver getDriver(){
        return driver;
    }
}