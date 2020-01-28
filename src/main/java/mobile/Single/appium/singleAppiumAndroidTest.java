package mobile.Single.appium;

import com.experitest.appium.SeeTestCapabilityType;
import com.experitest.appium.SeeTestClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileBrowserType;
import mobile.drivers.NewIOSDriver;
import utils.CloudServer;
import utils.Utilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import mobile.drivers.NewAndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class singleAppiumAndroidTest {
    private static CloudServer cloudServer;
    private AppiumDriver driver;
    DesiredCapabilities dc = new DesiredCapabilities();
    private String reportURL;
    SeeTestClient client;

    @Before
    public void setUp() throws MalformedURLException {

        cloudServer = new CloudServer(CloudServer.CloudServerNameEnum.MASTER);
        dc.setCapability("accessKey", cloudServer.ACCESSKEY);
//        dc.setCapability("deviceQuery", "@os='android'");
        dc.setCapability(MobileCapabilityType.UDID,"LGH85046996304");
//        dc.setCapability("generateReport", false);
//        dc.setCapability("takeScreenshots", false);
//        dc.setCapability("instrument",true);
//        dc.setCapability(MobileCapabilityType.APP,"com.experitest.uicatalog");
        dc.setCapability("newSessionWaitTimeout", 500);
        dc.setBrowserName(MobileBrowserType.CHROME);

        driver = new AndroidDriver(new URL(cloudServer.gridURL), dc);

        reportURL = (String) driver.getCapabilities().getCapability("reportUrl");
        System.out.println(reportURL);
        System.out.println(driver.getSessionId().toString());
        client = new SeeTestClient(driver);
    }

    @Test
    public void testExperitest() throws Exception {
        SeeTestClient c = new SeeTestClient(driver);
//        c.collectSupportData("C:\\temp","","","","","");
        client.setProperty("android.instrumentation.camera","true");
        client.install("cloud:com.experitest.uicatalog",true,true);
        client.launch("com.experitest.uicatalog/.MainActivity",true,true);
        client.simulateCapture("C:\\Users\\DELL\\Desktop\\LARGE_elevation.jpg");

        System.out.println("sout - " + driver.executeScript("seetest:client.run(\"adb shell pm list packages\")"));
        driver.get("https://www.bing.com/search?q=1");
        driver.get("https://www.bing.com/search?q=2");
        driver.get("https://www.bing.com/search?q=3");

//        driver.get("https://www.bing.com/search?q=3");
//        driver.get("https://www.bing.com/search?q=3");
//        driver.get("https://www.bing.com/search?q=3");


    }
    @After
    public void tearDown() {
        driver.quit();
    }

}
