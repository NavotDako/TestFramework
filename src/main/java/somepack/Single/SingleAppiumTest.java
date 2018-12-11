package somepack.Single;

import Utils.CloudServer;
import Utils.Utilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SingleAppiumTest {
    private static CloudServer cloudServer;
    private int iteration;
    private boolean remote = true;
    private String serial = "FA51DWM01338";
    String url;
    private AppiumDriver driver;
    DesiredCapabilities dc = new DesiredCapabilities();
    private String os;
    private String threadName;
    private String reportURL;


    @Before
    public void setUp() {
        System.out.println(Utilities.getTime() + "\t" + threadName + "\tCreating Android Driver...");
        cloudServer = new CloudServer(CloudServer.CloudServerNameEnum.MINE);
        os = cloudServer.getDeviceOSByUDID(serial);
        dc.setCapability("accessKey", cloudServer.ACCESSKEY);
        url = cloudServer.gridURL;

        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
        dc.setCapability(MobileCapabilityType.UDID, serial);

        //            dc.setCapability(MobileCapabilityType.APP, "cloud:uniqueName=android1542546850401");
//                    dc.setCapability(MobileCapabilityType.APP, "cloud:com.example.shaharyannay.dotgame/.Activity.LoginActivity");

//        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.example.shaharyannay.dotgame");
//        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".Activity.LoginActivity");
        try {
            driver = new AndroidDriver(new URL(url), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//            System.out.println(Utilities.getTime() + "\t" + threadName + "\tAndroid Driver Created");
    }

    @Test
    public void testExperitest() throws MalformedURLException {
        driver = new AndroidDriver(new URL(url), dc);
        reportURL = (String) driver.getCapabilities().getCapability("reportUrl");
        System.out.println(reportURL);
        driver.get("https://www.google.com");
//        Assert.fail("dddddd");
//        int i = 1 / 0;
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
