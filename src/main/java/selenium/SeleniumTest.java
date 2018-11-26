package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import Utils.CloudServer;
import Utils.Utilities;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class SeleniumTest implements Runnable {
    private static boolean single = false;
    private final String threadName;
    private final DesiredCapabilities dc;
    static URL url;
    private String sessionId;

    public SeleniumTest(String s, DesiredCapabilities dc) {
        this.dc = dc;
        threadName = s;
        if (!single) {
            url = SeleniumRunner.url;
        }
    }

    public static void main(String[] args) throws MalformedURLException {
        single = true;
        CloudServer cloudServer = new CloudServer(CloudServer.CloudServerNameEnum.ESXI);
        String ACCESS_KEY = cloudServer.ACCESSKEY;

        DesiredCapabilities dc = new DesiredCapabilities();

        url = new URL(cloudServer.gridURL);

        dc.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        dc.setCapability(CapabilityType.VERSION, "any");
        dc.setCapability(CapabilityType.PLATFORM, Platform.ANY);
        dc.setCapability("accessKey", ACCESS_KEY);
        dc.setCapability("testName", "Selenium test");
        dc.setCapability("takeScreenshots", true);
//        dc.setCapability("newCommandTimeout",1);

        SeleniumTest test = new SeleniumTest("Single", dc);
        System.out.println(cloudServer.gridURL);
        test.run();
        System.out.println("Done With The Test");
    }

    @Override
    public void run() {
        System.out.println(Utilities.getTime() + " " + threadName + ": Starting");
        RemoteWebDriver driver = null;
        String reportURL = null;
        try {
            driver = new RemoteWebDriver(url, dc);
            reportURL = (String) driver.getCapabilities().getCapability("reportUrl");
            sessionId = (String) driver.getCapabilities().getCapability("sessionId");
            System.out.println(Utilities.getTime() + " " + threadName + ": " + driver.getCapabilities().asMap().toString());

            if (!(driver.getCapabilities().getCapability(CapabilityType.BROWSER_NAME)).equals(BrowserType.IE)) {
                driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
            }

            System.out.println(Utilities.getTime() + " " + threadName + ": Driver Was Created");

            for (int i = 0; i < 20; i++) {
                System.out.println(Utilities.getTime() + " " + threadName + ": Iteration: " + i);
                driver.get("https://www.bing.com/search?q=" + i + "&qs=n&form=QBLH&sp=-1&pq=1&sc=8-1&sk=&cvid=DD72583E3B9844A89A07A0B3A35857A6");
                String value = driver.findElement(By.xpath("//*[@id='sb_form_q']")).getAttribute("value");
//                System.out.println("value: "+value);
                if (!value.equals("" + i)) throw new Exception("Value is Wrong!");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Utilities.log((Utilities.getTime() + " " + threadName + ": Success - " + reportURL + "\tSession: " + sessionId));

        } catch (Exception e) {

            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));

            System.err.println(Utilities.getTime() + " " + threadName + ": " + errors.toString());
            try {
                Utilities.log((Utilities.getTime() + " " + threadName + ": Failure - " + reportURL + "\tSession: " + sessionId + "\tError: " + e.getMessage().replaceAll("\n", "\t")));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {

            System.out.println(Utilities.getTime() + " " + threadName + ": Ending Test");

            if (driver != null) {
                driver.quit();
            } else {
                System.out.println(Utilities.getTime() + " " + threadName + ": Done - No Report - No Driver Created");

            }
            System.out.println(Utilities.getTime() + " " + threadName + ": Done - Report - " + reportURL);


        }

    }
}
