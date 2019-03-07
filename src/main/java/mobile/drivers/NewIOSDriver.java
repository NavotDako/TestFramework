package mobile.drivers;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.SessionId;

import java.net.URL;

public class NewIOSDriver extends IOSDriver {
    private String deviceID = null;
    private String deviceName = null;



    public NewIOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
//        if (Runner.GRID) System.out.println(this.getCapabilities().getCapability("cloudViewLink"));
        try {
            this.deviceID = (String) desiredCapabilities.getCapability("udid");
            this.deviceName = ((String) desiredCapabilities.getCapability("deviceName")).replace(" ", "_").replace("'", "-").trim();

        } catch (Exception e) {
            System.out.println("No Id or Name");
        }
    }

    @Override
    protected void log(SessionId sessionId, String commandName, Object toLog, When when) {

        System.out.println(String.format("%-70s%-100s", utils.Utilities.getTime() + "\t" + deviceID ,when + ": " + commandName + " toLog:" + toLog));
        super.log(sessionId, commandName, toLog, when);

    }


}
