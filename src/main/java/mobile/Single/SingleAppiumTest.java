package mobile.Single;

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

public class SingleAppiumTest {
    private static CloudServer cloudServer;
    private String serial = "P6Q7N15725000283";
    String url;
    private AppiumDriver driver;
    DesiredCapabilities dc = new DesiredCapabilities();
    private String os;
    private String reportURL;
    private boolean local = false;


    @Before
    public void setUp() {

        if (local) {
            url = "http://localhost:4723/wd/hub";
        } else {

            cloudServer = new CloudServer(CloudServer.CloudServerNameEnum.MINE);
           // os = cloudServer.getDeviceOSByUDID(serial);
            dc.setCapability("accessKey", cloudServer.ACCESSKEY);
            url = cloudServer.gridURL;
        }

        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
        dc.setCapability(MobileCapabilityType.UDID, serial);
        dc.setCapability("newSessionWaitTimeout", 36000);
        //            dc.setCapability(MobileCapabilityType.APP, "cloud:uniqueName=android1542546850401");
//                    dc.setCapability(MobileCapabilityType.APP, "cloud:com.example.shaharyannay.dotgame/.Activity.LoginActivity");

//        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.example.shaharyannay.dotgame");
//        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".Activity.LoginActivity");
        System.out.println(Utilities.getTime() + "\t" + serial + "\t" + "Creating Android Driver...");

        try {
            driver = new NewAndroidDriver(new URL(url), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(Utilities.getTime() + "\t" + serial + "\t" + "Done Android Driver...");
        reportURL = (String) driver.getCapabilities().getCapability("reportUrl");
        System.out.println(reportURL);
//            System.out.println(Utilities.getTime() + "\t" + threadName + "\tAndroid Driver Created");
    }

    @Test
    public void testExperitest() throws MalformedURLException {

        driver.get("https://www.google.com");
        try {
            driver.findElement(By.xpath("//*[@alt='tttt']"));

            new WebDriverWait(driver, 40).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='tttt']")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.get("https://www.google.com");

//        Assert.fail("dddddd");
//        int i = 1 / 0;
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
