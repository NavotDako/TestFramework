package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class seletest {
    public static void main(String[] args) {
        //
        System.setProperty("webdriver.chrome.driver", "bin/chromedriver.exe");

        WebDriver driver = new ChromeDriver();

    }

}
