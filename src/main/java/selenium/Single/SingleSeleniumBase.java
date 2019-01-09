package selenium.Single;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SingleSeleniumBase {
    protected URL url;
    protected RemoteWebDriver driver;
    protected DesiredCapabilities dc = new DesiredCapabilities();

    @Before
    public void setUp() {

        try {
            url = new URL("https://atb.experitest.com/wd/hub");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        dc.setCapability("username", "amit.nahum");
        dc.setCapability("password", "Zz123456");
        dc.setCapability("dddd", "sadsafas");
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
