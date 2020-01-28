package mobile.parallel.appiumClientTests.bases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import mobile.drivers.NewAndroidDriver;
import mobile.drivers.NewIOSDriver;
import mobile.parallel.appiumClientTests.appiumRunner;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.Utilities;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class base {
    String url;
    public AppiumDriver driver;
    DesiredCapabilities dc = new DesiredCapabilities();
    private String reportURL;
    private String UDID;


    @Before
    public void setUp() throws MalformedURLException {

        dc.setCapability("newSessionWaitTimeout", 500);
        dc.setCapability("accessKey", appiumRunner.cloudServer.ACCESSKEY);
        url = appiumRunner.cloudServer.gridURL;
        UDID = appiumRunner.map.get(Thread.currentThread().getName() + "_sn");
        dc.setCapability(MobileCapabilityType.UDID, appiumRunner.map.get(Thread.currentThread().getName() + "_sn"));

        getCapabilities();

        System.out.println(Utilities.getTime() + "\t" + Thread.currentThread().getName() + "\t" + "Creating Driver For - " + appiumRunner.map.get(Thread.currentThread().getName() + "_sn"));

        if (appiumRunner.map.get(Thread.currentThread().getName() + "_os").equals("ios")) {
            dc.setCapability("deviceName", "auto");

            dc.setCapability("appiumVersion", "1.10.0-p0");

            dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");

            driver = new NewIOSDriver(new URL(url), dc);
        } else {
            driver = new NewAndroidDriver(new URL(url), dc);
        }
        System.out.println(Utilities.getTime() + "\t" + Thread.currentThread().getName() + "\t" + "We Have A Driver For - " + appiumRunner.map.get(Thread.currentThread().getName() + "_sn"));

        getDataFromDriver();
    }

    private void getDataFromDriver() {
        reportURL = (String) driver.getCapabilities().getCapability("reportUrl");
        String sid = driver.getSessionId().toString();

        appiumRunner.map.put(Thread.currentThread().getName() + "_sid", sid.substring(sid.lastIndexOf("-") + 1));
        appiumRunner.map.put(Thread.currentThread().getName() + "_report", reportURL);

        System.out.println(reportURL);
        System.out.println(driver.getSessionId().toString());
    }

    @After
    public void tearDown() {
        System.out.println(Utilities.getTime() + "\t" + Thread.currentThread().getName() + "\t" + "Starting Tear Down For - " + UDID);
        driver.quit();
        System.out.println(Utilities.getTime() + "\t" + Thread.currentThread().getName() + "\t" + "Finish Tear Down For - " + UDID);

    }

    abstract void getCapabilities();
}
