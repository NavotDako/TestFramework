package mobile.drivers;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.SessionId;

import java.net.URL;

public class NewAndroidDriver extends AndroidDriver {
    private final String deviceID;
    private String deviceName = "";
    private String lastCommand = "";
    private boolean INSIDE = false;

    public NewAndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {

        super(remoteAddress, desiredCapabilities);
        this.deviceID = (String) desiredCapabilities.getCapability("udid");
        try {
            this.deviceName = ((String) desiredCapabilities.getCapability("deviceName")).replace(" ", "_").replace("'", "-").trim();
        } catch (Exception e) {
            this.deviceName = deviceID;
        }
        lastCommand = "";
    }


    @Override
    protected void log(SessionId sessionId, String commandName, Object toLog, When when) {

        super.log(sessionId, commandName, toLog, when);

        System.out.println(String.format("%-70s%-100s", utils.Utilities.getTime() + "\t" + deviceID ,when + ": " + commandName + " toLog:" + toLog));
//        if (deviceName != null) {
//            utils.writeToDeviceLog(deviceName, sdf.format(new Date(System.currentTimeMillis())) + when + ": " + commandName + " toLog:" + toLog);
//
//        }
    }
}
