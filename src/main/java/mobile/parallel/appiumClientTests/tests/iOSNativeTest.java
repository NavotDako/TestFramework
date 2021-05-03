package mobile.parallel.appiumClientTests.tests;

import io.appium.java_client.remote.MobileCapabilityType;
import mobile.parallel.appiumClientTests.bases.baseNative;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;

public class iOSNativeTest extends baseNative {


    @Test
    public void testExperitest() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        System.out.println(driver.getPageSource());
        driver.findElement(By.xpath("//*[@name='usernameTextField']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@name='passwordTextField']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@name='loginButton']")).click();
        driver.executeScript("Seetest:client.startPerformanceTransaction(\"\")");
        driver.findElement(By.xpath("//*[@name='makePaymentButton']")).click();
        driver.findElement(By.xpath("//*[@name='phoneTextField']")).sendKeys("0541234567");
        driver.findElement(By.xpath("//*[@name='nameTextField']")).sendKeys("Jon Snow");
        driver.findElement(By.xpath("//*[@name='amountTextField']")).sendKeys("50");
        driver.findElement(By.xpath("//*[@name='countryButton']")).click();
        driver.findElement(By.xpath("//*[@name='Switzerland']")).click();
        driver.findElement(By.xpath("//*[@name='sendPaymentButton']")).click();
        driver.findElement(By.xpath("//*[@name='Yes']")).click();
        driver.executeScript("Seetest:client.endPerformanceTransaction(\"EriBank.Transaction."+driver.getCapabilities().getCapability(MobileCapabilityType.UDID)+"\")");


    }



}
