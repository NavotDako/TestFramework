package somepack;

import Utils.CloudServer;
import Utils.Utilities;
import com.experitest.appium.SeeTestClient;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import somepack.drivers.NewAndroidDriver;
import somepack.drivers.NewIOSDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class AppiumTest implements Runnable {

    private static CloudServer cloudServer;
    private int iteration;
    private boolean remote = true;
    private String serial;

    private AppiumDriver driver;
    DesiredCapabilities dc = new DesiredCapabilities();
    private String os;
    private String threadName;
    private String reportURL;

    AppiumTest(String serial, int iteration) {
        this.serial = serial;
        this.iteration = iteration;
        if (iteration > -1) {
            cloudServer = Parallel.cloudServer;
        }
    }

    public static void main(String[] args) {
        cloudServer = new CloudServer(CloudServer.CloudServerNameEnum.MINE);
        AppiumTest test = new AppiumTest("HT51HWV00455", -1);
        Thread t = new Thread(test);
        t.start();
    }

    @Override
    public void run() {
        threadName = serial;
        try {
            setup();
        } catch (Exception e) {
            e.printStackTrace();
            handleSetupFailure(e);
            return;
        }

        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
            handleTestFailure(e);
        }

        try {
            tearDown();
        } catch (Exception e) {
            System.out.println(Utilities.getTime() + "\t" + threadName + " - Finish failed");
            e.printStackTrace();
        }

    }

    private void setup() throws MalformedURLException {
        System.out.println(Utilities.getTime() + "\t" + threadName + " - Starting Setup");
        String url;

        if (remote) {
            os = cloudServer.getDeviceOSByUDID(serial);
            dc.setCapability("accessKey", cloudServer.ACCESSKEY);
            url = cloudServer.gridURL;
        } else {
            url = "http://localhost:8889/wd/hub";
        }
//        dc.setCapability("reportDirectory", "c:\\temp");
        dc.setCapability("reportFormat", "xml");
        dc.setCapability("newSessionWaitTimeout", "600");

        if (os.equals("ios")) {
            dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "ios");
            dc.setCapability(MobileCapabilityType.DEVICE_NAME, "ios");
            dc.setCapability(MobileCapabilityType.UDID, serial);
            dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");

            dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
//            System.out.println(Utilities.getTime() + "\t" + threadName + "\tCreating iOS Driver...");
            driver = new NewIOSDriver(new URL(url), dc);
//            System.out.println(Utilities.getTime() + "\t" + threadName + "\tiOS Driver Created");

        } else {
            dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
            dc.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
            dc.setCapability(MobileCapabilityType.UDID, serial);
//            dc.setCapability(MobileCapabilityType.APP, "cloud:uniqueName=android1542546850401");
            dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");

            dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
            dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
//            System.out.println(Utilities.getTime() + "\t" + threadName + "\tCreating Android Driver...");
            driver = new NewAndroidDriver(new URL(url), dc);
//            System.out.println(Utilities.getTime() + "\t" + threadName + "\tAndroid Driver Created");
        }
        reportURL = (String) driver.getCapabilities().getCapability("reportUrl");
        System.out.println(Utilities.getTime() + "\t" + threadName + " - Setup Passed - "+reportURL);

    }

    private void test() throws InterruptedException {

        System.out.println(Utilities.getTime() + "\t" + threadName + " - Test Starting");

        SeeTestClient c = new SeeTestClient(driver);
        c.getCurrentApplicationName();
        driver.closeApp();

        c.launch("http://www.google.com", true, true);
//        List<Long> i = (List<Long>) driver.executeScript("return [1,2,3]");

        try {
            driver.findElement(By.xpath("//G-FLAT-BUTTON")).click();
        } catch (Exception e) {
        }

        try {
            driver.findElement(By.xpath("//*[@id='infobar_close_button']")).click();
        } catch (Exception e) {
        }

        driver.context("WEBVIEW_1");
        driver.findElement(By.xpath("//*[@name='q']")).sendKeys("experitest");
        driver.findElement(By.xpath("//*[@class='Tg7LZd']")).click();

        tryToHandlePopup("//*[@text='Change to English' and @class='ZyXQnc']");
        tryToHandlePopup("//*[@id='button_secondary' and @text='Block']");

        driver.findElement(By.xpath("//*[@text='https://experitest.com' or (@text='Experitest' and @class='MUxGbd v0nnCb') or (@text='https://experitest.com' and @class='qzEoUe')]")).click();
        try {
            driver.findElement(By.xpath("//*[@text='https://experitest.com' or (@text='Experitest' and @class='MUxGbd v0nnCb') or (@text='https://experitest.com' and @class='qzEoUe')]")).click();
        } catch (Exception e) {

        }
        new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@alt='Experitest Logo']")));
        driver.findElement(By.xpath("//*[@alt='Experitest Logo']"));
        SeeTestClient client = new SeeTestClient(driver);
        client.setReportStatus("Passed","NICE");
        logTestPassed();
    }

    private void tryToHandlePopup(String pop) {
        driver.context("native");
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(pop)));
            Thread.sleep(2000);
            driver.findElement(By.xpath(pop)).click();
            driver.findElement(By.xpath(pop)).click();
            driver.findElement(By.xpath(pop)).click();
            driver.findElement(By.xpath(pop)).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath(pop)).click();
        } catch (Exception e) {
        }
        driver.context("WEBVIEW_1");
    }

    private void tearDown() {
        System.out.println(Utilities.getTime() + "\t" + threadName + " - Ending Test");
        System.out.println(driver.getCapabilities().toString());
        driver.quit();
        System.out.println(Utilities.getTime() + "\t" + threadName + " - Done");
    }

    private void handleTestFailure(Exception e) {
        driver.getPageSource();
        System.out.println(Utilities.getTime() + "\t" + threadName + " -- Failed --");
        try {
            Utilities.log(Utilities.getTime() + "\titeration - " + iteration + "\t -- Failed --\t" + String.format("%+50s",threadName) + "\t" + reportURL + "\t" + e.getMessage().substring(0, e.getMessage().indexOf("\n")));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        e.printStackTrace();
    }

    private void handleSetupFailure(Exception e) {
        try {
            Utilities.log(Utilities.getTime() + "\titeration - " + iteration + "\tSetup Failed\t" + String.format("%+50s",threadName) + "\t" + e.getMessage().substring(0, e.getMessage().indexOf("\n")));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println(Utilities.getTime() + "\t" + threadName + " - Setup failed");
        e.printStackTrace();
    }

    private void logTestPassed() {
        System.out.println(Utilities.getTime() + "\t" + threadName + " - Test Passed");

        try {
            Utilities.log(Utilities.getTime() + "\titeration - " + iteration + "\tPassed\t" + String.format("%+50s",threadName) + "\t" + reportURL);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}