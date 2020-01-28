package mobile.parallel.appiumClientTests.bases;

import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class baseNative extends base {

    @Override
    void getCapabilities() {
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank:3138");
        dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
    }
}
