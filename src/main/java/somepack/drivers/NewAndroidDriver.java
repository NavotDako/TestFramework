package somepack.drivers;

import com.sun.javafx.binding.StringFormatter;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.SessionId;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewAndroidDriver extends AndroidDriver {
    private final String deviceID;
    private String deviceName = "";
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
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

        if (commandName.equals("newSession")) sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        super.log(sessionId, commandName, toLog, when);

        System.out.println(String.format("%-70s%-100s",sdf.format(new Date(System.currentTimeMillis())) + ": " + deviceID ,when + ": " + commandName + " toLog:" + toLog));
//        if (deviceName != null) {
//            Utils.writeToDeviceLog(deviceName, sdf.format(new Date(System.currentTimeMillis())) + when + ": " + commandName + " toLog:" + toLog);
//
//        }
    }
}
