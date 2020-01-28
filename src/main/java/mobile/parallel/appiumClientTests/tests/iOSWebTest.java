package mobile.parallel.appiumClientTests.tests;

import io.appium.java_client.remote.MobileCapabilityType;
import mobile.parallel.appiumClientTests.bases.baseNative;
import mobile.parallel.appiumClientTests.bases.baseWeb;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class iOSWebTest extends baseWeb {
    @Test
    public void testExperitest() {
        driver.executeScript("Seetest:client.startPerformanceTransaction(\"\")");
        driver.get("https://www.google.com");
        System.out.println(driver.getContextHandles());
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        driver.findElement(By.name("q")).sendKeys("1");
//        System.out.println(driver.getPageSource());
        driver.findElement(By.xpath("//*[@css='BUTTON.Tg7LZd']")).click();
        driver.executeScript("Seetest:client.endPerformanceTransaction(\"WEB.Transaction."+driver.getCapabilities().getCapability(MobileCapabilityType.UDID)+"\")");

    }
}
