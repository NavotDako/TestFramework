package mobile.parallel;

import utils.CloudServer;
import utils.Utilities;
import com.experitest.appium.SeeTestClient;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import mobile.drivers.NewAndroidDriver;
import mobile.drivers.NewIOSDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
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
    SeeTestClient client = null;

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
//        dc.setCapability("reportDirectory", "client:\\temp");
        dc.setCapability("reportFormat", "xml");
        dc.setCapability("testName", serial + "_" + iteration);
        dc.setCapability("newSessionWaitTimeout", "600");

        if (os.equals("ios")) {
            dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "ios");
            dc.setCapability(MobileCapabilityType.DEVICE_NAME, "ios");
            dc.setCapability(MobileCapabilityType.UDID, serial);
//            dc.setCapability(MobileCapabilityType.APP, "cloud:uniqueName=unique");
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
        System.out.println(Utilities.getTime() + "\t" + threadName + " - Setup Passed - " + reportURL);

//        driver.manage().timeouts().implicitlyWait()
        client = new SeeTestClient(driver);

//        client.startLoggingDevice("c:\\Temp\\" + threadName + "\\" + Utilities.getTimeForFileName() + ".log");
//        String s = client.uploadFile("C:\\Users\\DELL\\Desktop\\aaaaaa.jpg");
//        System.out.println(s);
//        client.run("adb push /private/var/folders/zz/zyxvpxvq6csfxvn_n0000000000000/T/headless/"+s+" /sdcard");
    }

    private void test() {

        System.out.println(Utilities.getTime() + "\t" + threadName + " - Test Starting");
        System.out.println(Utilities.getTime() + "\t" + threadName + " - getCurrentApplicationName - " + client.getCurrentApplicationName().replaceAll("\n", "\t"));

        if (!os.equals("ios")) {
            client.run("adb shell settings put system screen_brightness 200");
            client.startStepsGroup("Company company");
            tryToHandleNativePopup("//*[@text='Confirm']");
            driver.findElement(By.xpath("//*[@id='usernameTextField']")).sendKeys("company");
            driver.findElement(By.xpath("//*[@id='passwordTextField']")).sendKeys("company");
            driver.findElement(By.xpath("//*[@text='Login']")).click();
            client.stopStepsGroup();

        }
        Set<String> contexts = driver.getContextHandles();
        for (String context : contexts) {
            System.out.println(Utilities.getTime() + "\t" + threadName + "\tContext: " + context);
        }
        driver.closeApp();
        client.launch("http://www.bing.com", true, true);
        driver.context("WEBVIEW_1");

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id='sb_form_q']")).sendKeys("experitest");
        driver.findElement(By.xpath("//*[(@id='sbBtn' and @alt='Search') or (@id='sbBtn' and @type='submit') or @id='sb_form_go']")).click();
//        driver.findElement(By.xpath("//*[@text='Experitest - Web & mobile app testing' or @text='https://experitest.com' or (@text='Experitest' and @class='MUxGbd v0nnCb') or (@text='https://experitest.com' and @class='qzEoUe')]")).click();
//        driver.findElement(By.xpath("//*[@alt='Experitest Logo']"));
        //  googleTest();
        driver.findElement(By.xpath("//*[@text='Mobile App Testing Tools – Experitest']"));

        client.setReportStatus("Passed", "NICE");

        logTestPassed();
    }

    private void googleTest() {
        client.launch("http://www.google.com", true, true);
//        driver.manage().timeouts().pageLoadTimeout(80, TimeUnit.SECONDS);

        try {
            driver.findElement(By.xpath("//G-FLAT-BUTTON")).click();
        } catch (Exception e) {
        }

        try {
            driver.findElement(By.xpath("//*[@id='infobar_close_button']")).click();
        } catch (Exception e) {
        }

        driver.context("WEBVIEW_1");

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@name='q']")).sendKeys("experitest");
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

        driver.findElement(By.xpath("//*[@class='Tg7LZd']")).click();

        tryToHandleNativePopup("//*[@text='Change to English']");
        tryToHandleNativePopup("//*[@id='button_secondary' and @text='Block']");
        tryToHandleNativePopup("//*[@text='Change to English']");
        tryToHandleWebPopup("//*[@text='Change to English' and @class='ZyXQnc']");

        driver.findElement(By.xpath("//*[@text='https://experitest.com' or (@text='Experitest' and @class='MUxGbd v0nnCb') or (@text='https://experitest.com' and @class='qzEoUe')]")).click();
        try {
            driver.findElement(By.xpath("//*[@text='https://experitest.com' or (@text='Experitest' and @class='MUxGbd v0nnCb') or (@text='https://experitest.com' and @class='qzEoUe')]")).click();
        } catch (Exception e) {
        }

        driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
        try {
            driver.findElement(By.xpath("//*[@alt='Experitest Logo']"));
        } catch (Exception e) {
            driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
            try {
                driver.findElement(By.xpath("//*[@text='https://experitest.com' or (@text='Experitest' and @class='MUxGbd v0nnCb') or (@text='https://experitest.com' and @class='qzEoUe')]")).click();
            } catch (Exception e1) {
            }

            driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//*[@alt='Experitest Logo']"));

        }
    }

    private void tryToHandleWebPopup(String pop) {
        client.startStepsGroup("Finding " + pop);
        String context = driver.getContext();
        driver.context("WEBVIEW_1");

        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(pop)));
            Thread.sleep(2000);
            driver.findElement(By.xpath(pop)).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath(pop)).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath(pop)).click();
        } catch (Exception e) {
            driver.getPageSource();
        }
        driver.context(context);
        client.stopStepsGroup();
    }

    private void tryToHandleNativePopup(String pop) {
        client.startStepsGroup("Finding " + pop);
        String context = driver.getContext();
        driver.context("NATIVE_APP");
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(pop)));
            Thread.sleep(2000);
            driver.findElement(By.xpath(pop)).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath(pop)).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath(pop)).click();
        } catch (Exception e) {
            driver.getPageSource();
        }
        driver.context(context);
        client.stopStepsGroup();
    }

    private void tearDown() {
        try {
//            client.stopLoggingDevice();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Utilities.getTime() + "\t" + threadName + " - Ending Test");
        System.out.println(Utilities.getTime() + "\t" + driver.getCapabilities().toString());
        driver.quit();
        System.out.println(Utilities.getTime() + "\t" + threadName + " - Done");
    }

    private void handleTestFailure(Exception e) {
        System.out.println(Utilities.getTime() + "\t" + threadName + " -- Failed --");
        e.printStackTrace();
        try {
            driver.getPageSource();
        } catch (Exception e1) {
            System.out.println(Utilities.getTime() + "\t" + threadName + " - getPageSource Failed");
        }
        try {
            Utilities.log(Utilities.getTime() + "\titeration - " + iteration + "\t -- Failed --\t" + String.format("%-40s", threadName) + "\t" + reportURL + "\t" + e.getMessage().substring(0, e.getMessage().indexOf("\n")));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void handleSetupFailure(Exception e) {
        try {
            Utilities.log(Utilities.getTime() + "\titeration - " + iteration + "\tSetup Failed\t" + String.format("%-40s", threadName) + "\t" + e.getMessage().substring(0, e.getMessage().indexOf("\n")));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println(Utilities.getTime() + "\t" + threadName + " - Setup failed");
        e.printStackTrace();
    }

    private void logTestPassed() {
        System.out.println(Utilities.getTime() + "\t" + threadName + " - Test Passed");

        try {
            Utilities.log(Utilities.getTime() + "\titeration - " + iteration + "\tPassed\t" + String.format("%-40s", threadName) + "\t" + reportURL);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}