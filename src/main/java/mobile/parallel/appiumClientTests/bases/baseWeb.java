package mobile.parallel.appiumClientTests.bases;

import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import mobile.parallel.appiumClientTests.appiumRunner;

public class baseWeb extends base {

    @Override
    void getCapabilities() {
        dc.setCapability("webkitResponseTimeout", "300000");
        dc.setBrowserName(MobileBrowserType.SAFARI);
        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, appiumRunner.cloudServer.getDeviceVersion(appiumRunner.map.get(Thread.currentThread().getName()+"_sn")));
    }
}
