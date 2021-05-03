package mobile.parallel.appiumClientTests.single;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import mobile.drivers.NewAndroidDriver;
import mobile.drivers.NewIOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.CloudServer;
import utils.Utilities;

import java.net.MalformedURLException;
import java.net.URL;

public class singleTestAppiumNative {

    //<docker-server-url>http://192.168.3.18:8080</docker-server-url>
    public AppiumDriver driver;
    DesiredCapabilities dc = new DesiredCapabilities();

    @Before
    public void setUp() throws MalformedURLException {
        CloudServer cs = new CloudServer(CloudServer.CloudServerNameEnum.QA);
        dc.setCapability("newSessionWaitTimeout", 500);
        dc.setCapability("accessKey", cs.ACCESSKEY);

//        dc.setCapability("deviceQuery", "@os='ios'");
        dc.setCapability(MobileCapabilityType.UDID, "00008020-000C1D5A0E89002E");

//        dc.setCapability("deviceName", "auto");
//        dc.setCapability("appiumVersion", "1.10.0-p0");
//        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
        dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");

        driver = new NewIOSDriver(new URL(cs.gridURL), dc);

    }

    @Test
    public void testExperitest() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        System.out.println(driver.getPageSource());
//        driver.findElement(By.xpath("//*[@name='usernameTextField']")).sendKeys("company");
//        driver.findElement(By.xpath("//*[@name='passwordTextField']")).sendKeys("company");
//        driver.findElement(By.xpath("//*[@name='loginButton']")).click();
//        driver.findElement(By.xpath("//*[@name='makePaymentButton']")).click();
//        driver.findElement(By.xpath("//*[@name='phoneTextField']")).sendKeys("0541234567");
//        driver.findElement(By.xpath("//*[@name='nameTextField']")).sendKeys("Jon Snow");
//        driver.findElement(By.xpath("//*[@name='amountTextField']")).sendKeys("50");
//        driver.findElement(By.xpath("//*[@name='countryButton']")).click();
//        driver.findElement(By.xpath("//*[@name='Switzerland']")).click();
//        driver.findElement(By.xpath("//*[@name='sendPaymentButton']")).click();
//        driver.findElement(By.xpath("//*[@name='Yes']")).click();

    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
