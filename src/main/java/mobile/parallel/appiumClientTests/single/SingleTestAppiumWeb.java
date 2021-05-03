package mobile.parallel.appiumClientTests.single;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;

import mobile.drivers.NewIOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CloudServer;


import java.net.MalformedURLException;
import java.net.URL;

public class SingleTestAppiumWeb {
    public NewIOSDriver driver;
    public String currentXpath;
    DesiredCapabilities dc = new DesiredCapabilities();

    @Before
    public void setUp() throws MalformedURLException {
        CloudServer cs = new CloudServer(CloudServer.CloudServerNameEnum.MINE);
        dc.setCapability("accessKey", cs.ACCESSKEY);
        dc.setCapability("newSessionWaitTimeout", 500);

        dc.setCapability(MobileCapabilityType.UDID, "<>");
        dc.setCapability("webkitResponseTimeout", "300000");
        dc.setBrowserName(MobileBrowserType.SAFARI);

        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "<>");

        dc.setCapability("deviceName", "auto");
        dc.setCapability("appiumVersion", "1.10.0-p0");
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");

        driver = new NewIOSDriver(new URL("<>"), dc);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testExperitest() {
        driver.executeScript("Seetest:client.startPerformanceTransaction(\"\")");
        driver.get("https://www.google.com");
        System.out.println(driver.getContextHandles());
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        WebElement x = driver.findElement(By.name("q"));
        x.sendKeys("1");

//        System.out.println(driver.getPageSource());

        driver.findElement(By.xpath(currentXpath)).click();
        driver.executeScript("Seetest:client.endPerformanceTransaction(\"WEB.Transaction." + driver.getCapabilities().getCapability(MobileCapabilityType.UDID) + "\")");

    }
}
