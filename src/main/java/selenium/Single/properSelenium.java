package selenium.Single;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.SourceType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class properSelenium {


    public static void main(String[] args) throws InterruptedException, IOException {

        for (int i=0;i<10;i++) {
            tr t = new tr();
            Thread th = new Thread(t);
            th.start();
        }
//        Thread.sleep(1000);
//        driver.get("https://qacloud.experitest.com/reporter/attachments/1/2019-11-25/48744/video4562971292174020130.mp4");


    }
}
class tr implements Runnable{
    protected WebDriver driver;
    protected DesiredCapabilities dc = new DesiredCapabilities();

    @Override
    public void run() {
        System.setProperty("webdriver.chrome.driver", "bin/chromedriver.exe");
        driver = new ChromeDriver();
        System.out.println("Launching url..");
        driver.get("https://qacloud.experitest.com/");
        driver.findElement(By.name("username")).sendKeys("eyal");
        driver.findElement(By.name("password")).sendKeys("Experitest2012");
        driver.findElement(By.name("login")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("https://qacloud.experitest.com/reporter");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("https://qacloud.experitest.com/reporter/html-report/index.html?test_id=48744");
        driver.quit();
    }
}
