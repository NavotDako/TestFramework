package mobile.Single.pageObjects;

import mobile.drivers.NewAndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.CloudServer;

import java.net.MalformedURLException;
import java.net.URL;


public class ListsAndFindByTest {

//    private RemoteWebDriver webDriver;
//
//    private ListsAndFindByPage page;
//
//    @BeforeClass
//    public void beforeClass() throws MalformedURLException {
//
//        DesiredCapabilities dc = new DesiredCapabilities();
//        CloudServer c = new CloudServer(CloudServer.CloudServerNameEnum.QA);
//        String url = c.gridURL;//cloud Server URL;
//        dc.setCapability("accessKey", c.ACCESSKEY);
//        dc.setCapability("deviceQuery", "@os='android' and @version!='8.0.0'");
//
//        webDriver = new NewAndroidDriver(new URL(url), dc);
//
//        String reportURL = (String) webDriver.getCapabilities().getCapability("reportUrl");
//        System.out.println(reportURL);
//        System.out.println(webDriver.getSessionId().toString());
//
//        page = PageFactory.initElements(webDriver, ListsAndFindByPage.class);
//    }
//
//    @AfterClass
//    public void afterClass() {
//        webDriver.quit();
//    }
//
//    @Test
//    public void atest() {
//        webDriver.get("https://www.bing.com");
//        WebElement element = page.getElement();
//        element.sendKeys("aaa");
//        element.sendKeys("bbb");
//        element.sendKeys("ccc");
//        element.sendKeys("ddd");
//        element.toString();
//        webDriver.get("http://192.168.2.170:8888");
//        element.toString();
//
////        WebElement elementFailure = page.getElementFailure();
//
//    }
}