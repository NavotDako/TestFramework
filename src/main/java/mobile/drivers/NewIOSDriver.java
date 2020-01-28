package mobile.drivers;

import io.appium.java_client.ios.IOSDriver;
import mobile.parallel.appiumClientTests.appiumRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.SessionId;

import java.net.URL;

public class NewIOSDriver extends IOSDriver {
    private String deviceID = null;
    private String deviceName = null;
    private String currentXpath;


    public NewIOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
        try {
            this.deviceID = appiumRunner.map.get(Thread.currentThread().getName() + "_sn");
        }catch (Exception e){

        }
        //        try {
//            this.deviceID = (String) desiredCapabilities.getCapability("udid");
//            this.deviceName = ((String) desiredCapabilities.getCapability("deviceName")).replace(" ", "_").replace("'", "-").trim();
//        } catch (Exception e) {
//            System.out.println("No Id or Name");
//        }
    }

    @Override
    protected void log(SessionId sessionId, String commandName, Object toLog, When when) {
        System.out.println(String.format("%-70s%-100s", utils.Utilities.getTime() + "\t" + deviceID, when + ": " + commandName + "\t" + toLog));

        super.log(sessionId, commandName, toLog, when);

    }


    public WebElement2 findElement2(By by) {
        WebElement2 x = new WebElement2(by);
        super.findElement(by);
        return x;
    }

    @Override
    public WebElement findElement(By by) {
        return super.findElement(by);
    }
}
