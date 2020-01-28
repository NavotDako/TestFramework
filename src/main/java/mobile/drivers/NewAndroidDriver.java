package mobile.drivers;

import io.appium.java_client.android.AndroidDriver;
import mobile.parallel.appiumClientTests.appiumRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.SessionId;

import java.net.URL;

public class NewAndroidDriver extends AndroidDriver {
    private final String deviceID;
    private String deviceName = "";
    private String lastCommand = "";
    private boolean INSIDE = false;

    public NewAndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {

        super(remoteAddress, desiredCapabilities);
        this.deviceID = appiumRunner.map.get(Thread.currentThread().getName() + "_sn");

//        this.deviceID = (String) desiredCapabilities.getCapability("udid");
//        try {
//            this.deviceName = ((String) desiredCapabilities.getCapability("deviceName")).replace(" ", "_").replace("'", "-").trim();
//        } catch (Exception e) {
//            this.deviceName = deviceID;
//        }
//        lastCommand = "";
    }


    @Override
    protected void log(SessionId sessionId, String commandName, Object toLog, When when) {
        super.log(sessionId, commandName, toLog, when);
        System.out.println(String.format("%-70s%-100s", utils.Utilities.getTime() + "\t" + deviceID, when + ": " + commandName + "\t" + toLog));
    }

    @Override
    public WebElement findElement(By by) {

        this.executeScript("seetest:client.setShowReport(false)");
        WebElement a = null;
        try {
            a = super.findElement(by);
        } catch (Exception e) {
            this.executeScript("seetest:client.setShowReport(true)");
            return super.findElement(by);
        }
        this.executeScript("seetest:client.setShowReport(true)");
        return a;
    }
}
