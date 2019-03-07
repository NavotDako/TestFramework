package selenium.Single;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class properSelenium {
    protected static WebDriver driver;
    protected DesiredCapabilities dc = new DesiredCapabilities();

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\DELL\\Desktop\\testP\\testP\\lib\\chromedriver.exe");
        driver = new ChromeDriver();
        System.out.println("Launching url..");
        driver.get("https://fantasy.premierleague.com/a/leagues/standings/313/classic");
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"ismr-classic-standings\"]/div/div/table/tbody/tr/td/a"));
        System.out.println("elements size - " + elements.size());
        if (elements.size() == 0) {
            elements = driver.findElements(By.xpath("//*[@id=\"ismr-classic-standings\"]/div/div/table/tbody/tr/td/a"));
        }
        List<String> teamsURL = new ArrayList<>();
        for (WebElement element : elements) {
            System.out.println(element.getAttribute("href"));
            teamsURL.add(element.getAttribute("href"));
        }

        for (int i = 0; i < teamsURL.size(); i++) {
            System.out.println("---------------------------------------------");
            System.out.println("Team " + teamsURL.get(i));
            driver.get(teamsURL.get(i));
            WebDriverWait wait = new WebDriverWait(driver,10);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"ismr-main\"]/div/section[1]/div[1]/div/ul/li[2]/a")));
            driver.findElement(By.xpath("//*[@id=\"ismr-main\"]/div/section[1]/div[1]/div/ul/li[2]/a")).click();

            List<WebElement> playerElements = driver.findElements(By.xpath("//*[@id=\"ismr-detail\"]/div/div[1]/div/table/tbody/tr"));

            List<Player> players = new ArrayList<>();

            for (int j = 1; j <= playerElements.size(); j++) {
                Player p = new Player(driver.findElement(By.xpath("//*[@id=\"ismr-detail\"]/div/div[1]/div/table/tbody/tr["+j+"]/td[2]/div/div[2]/a")).getText(), driver.findElement(By.xpath("//*[@id=\"ismr-detail\"]/div/div[1]/div/table/tbody/tr["+j+"]/td[2]/div/div[2]/span")).getText());
                players.add(p);
                System.out.println(p);
            }

            System.out.println("Banch - ");
            for (int j = 1; j <= 4; j++) {

                Player p = new Player(driver.findElement(By.xpath("//*[@id=\"ismr-detail\"]/div/div[2]/div/table/tbody/tr["+j+"]/td[2]/div/div[2]/a")).getText(), driver.findElement(By.xpath("//*[@id=\"ismr-detail\"]/div/div[2]/div/table/tbody/tr["+j+"]/td[2]/div/div[2]/span")).getText());
                players.add(p);
                System.out.println(p);
            }

        }

        driver.quit();
    }
}

class Player {
    String name;
    String team;
    String position;

    public Player(String name, String team) {
        this.name = name;
        this.team = team;
    }

    @Override
    public String toString() {
        return "Player - " + name + "\t" + "Team - " + team;
    }
}