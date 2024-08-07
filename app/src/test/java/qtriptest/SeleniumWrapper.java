package qtriptest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumWrapper {

    public static Boolean click(WebElement elementToClick,WebDriver driver){
        try{
            if(!elementToClick.isDisplayed()){
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true)", elementToClick);
            }
            elementToClick.click();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean SendKeys(WebElement inputBox,String keysToSend){
        try{
            inputBox.clear();
            inputBox.sendKeys(keysToSend);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean NavigatetoURL(WebDriver driver,String url){
        try{
            if(!url.equals(driver.getCurrentUrl())){
                driver.get(url);
                
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // public static WebElement findElementWithRetry(WebDriver driver,By by,int retryCount){
    //     try{
    //         WebElement ss = driver.findElement(by);
    //         if(ss.isDisplayed())
    //             return true;
    //     }catch(Exception e){
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

}
