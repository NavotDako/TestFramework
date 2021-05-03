package selenium;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileBrowserType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CloudServer;

import java.net.URL;
import java.util.concurrent.TimeUnit;


public class standAlone {

    private RemoteWebDriver driver;
    private URL url;
    private DesiredCapabilities dc = new DesiredCapabilities();

    @Before
    public void setUp() throws Exception {
        CloudServer cs = new CloudServer(CloudServer.CloudServerNameEnum.DEEP);
        url = new URL(cs.gridURL);
        dc.setCapability("accessKey", cs.ACCESSKEY);
        dc.setCapability("testName", "Quick");
        dc.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
        dc.setBrowserName(MobileBrowserType.SAFARI);
        driver = new IOSDriver(url, dc);
    }


    @Test
    public void browserTestGoogleSearch() {
        for (int i = 0; i < 1; i++) {
            driver.get("https://deliveryqa-staging.pnc.com/en/apps/loans/auto-loans.html");
            driver.findElement(By.xpath("//*[@id=\"input_0\"]")).sendKeys("ddddd");


        }
    }

    @After
    public void tearDown() {
        System.out.println("Report URL: " + driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }

}