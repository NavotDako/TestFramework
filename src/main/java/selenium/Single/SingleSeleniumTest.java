package selenium.Single;

import com.experitest.reporter.junit4.ReporterRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.RemoteWebDriver;


@RunWith(ReporterRunner.class)
public class SingleSeleniumTest extends SingleSeleniumBase {



    @Test
    public void testExperitest() {
        driver = new RemoteWebDriver(url, dc);
        System.out.println((String) driver.getCapabilities().getCapability("reportUrl"));
        driver.get("https://www.google.com");
        Assert.fail("dddddd");
        int i = 1 / 0;
    }

}
