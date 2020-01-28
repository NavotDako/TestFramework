package mobile.appiumDocker;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.boon.Runner;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.DesiredCapabilities;

import static utils.Utilities.getTime;

public class appiumDockerBaseTest {
    protected boolean status = false;
    protected AppiumDriver driver = null;

    public DesiredCapabilities getBasicCapabilities() {
        System.out.println(getTime() + Thread.currentThread().getName() + "\tsetUp - " + appiumDockerRunner.map.get(Thread.currentThread().getName()) + "\t" + getClass().getName());

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability("udid", appiumDockerRunner.map.get(Thread.currentThread().getName()));
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, "auto");

        if (appiumDockerRunner.OSS) {
            dc.setCapability("testName", getClass().getName());
            dc.setCapability("appiumVersion", "1.10.0-p0");
            dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        }else{
            dc.setCapability("testName",  getClass().getName() +"- NOT OSS");
        }

        dc.setCapability("accessKey", appiumDockerRunner.accessKey);

//        dc.setCapability("username", "admin");
//        dc.setCapability("password", "Experitest2012");
        return dc;
    }

    @After
    public void tearDown() {
//        if (!status) {
//            driver.getPageSource();
//        }

        driver.quit();

    }
    @Rule
    public TestWatcher watchman= new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {

        }

        @Override
        protected void succeeded(Description description) {

        }
    };

}
