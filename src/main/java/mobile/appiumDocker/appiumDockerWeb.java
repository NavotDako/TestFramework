package mobile.appiumDocker;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileBrowserType;
import mobile.drivers.NewIOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class appiumDockerWeb extends appiumDockerBaseTest {

    DesiredCapabilities dc = new DesiredCapabilities();

    @Before
    public void setUp() throws MalformedURLException {

        dc = getBasicCapabilities();

        dc.setBrowserName(MobileBrowserType.SAFARI);

        driver = new NewIOSDriver(new URL(appiumDockerRunner.cloud), dc);
        System.out.println(driver.getCapabilities().getCapability(""));
    }

    @Test
    public void testUntitled() {
        driver.get("https://www.google.com");
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        WebElement searchBar = driver.findElement(By.name("q"));
        searchBar.sendKeys("Experitest");

        for (int i = 0; i < 5; i++) {
            driver.get("https://www.bing.com");
            driver.findElement(By.xpath("//*[@name='q']")).sendKeys("" + i);
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='sbBtn' or @id='sb_form_go']")));
            driver.findElement(By.xpath("//*[@id='sbBtn' or @id='sb_form_go']")).click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@name='q' and @value='" + i + "']")));
            driver.findElement(By.xpath("//*[@name='q' and @value='" + i + "']"));
        }

    }


}