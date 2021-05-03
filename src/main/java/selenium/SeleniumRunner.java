package selenium;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.CloudServer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SeleniumRunner {

    static URL url;
    static Random r;

    public static void main(String[] args) throws MalformedURLException {
        String ACCESS_KEY;
        int threadNum = 40;
        r = new Random();

        CloudServer cloudServer = new CloudServer(CloudServer.CloudServerNameEnum.ATB);
        ACCESS_KEY = cloudServer.ACCESSKEY;
        url = new URL(cloudServer.gridURL);

        for (int j = 0; j < 10; j++) {
            ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
            List<Future> res = new LinkedList<>();

            for (int i = 0; i < threadNum; i++) {
                DesiredCapabilities dc = new DesiredCapabilities();

                dc.setCapability(CapabilityType.BROWSER_NAME, getRandomBrowserType());
                dc.setCapability(CapabilityType.VERSION, "any");
                dc.setCapability(CapabilityType.PLATFORM, Platform.ANY);
                dc.setCapability("accessKey", ACCESS_KEY);
                dc.setCapability("takeScreenshots", true);
                dc.setCapability("testName", "Test No. " + i);
                dc.setCapability("newSessionWaitTimeout", 60 * 60 * 8);

                SeleniumTest test = new SeleniumTest("Thread-" + i, dc);
                res.add(executorService.submit(test));
            }

            System.out.println("Starting All Threads");

            for (Future re : res) {
                try {
                    re.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            executorService.shutdown();
        }
        System.out.println("Done With All Tests");
    }

    private static String getRandomBrowserType() {
        switch (r.nextInt(4)){
            case 0:{
                return BrowserType.CHROME;
            }
            case 1:{
                return BrowserType.SAFARI;
            }
            case 2:{
                return BrowserType.FIREFOX;
            }
            case 3:{
                return BrowserType.IE;
            }
        }
        return BrowserType.CHROME;
    }
}
