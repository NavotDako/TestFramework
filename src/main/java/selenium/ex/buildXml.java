package selenium.ex;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;

public class buildXml {
    protected static WebDriver driver;
    protected DesiredCapabilities dc = new DesiredCapabilities();

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "bin/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://fantasy.premierleague.com/a/leagues/standings/313/classic");
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        String s = driver.getPageSource();


        s = getBody(s);

        s = removeComments(s);

        s = removeUnsupportedCharacters(s);

        while (s.indexOf("\n") > 0) {
            s = s.replace("\n", "");
        }

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(s)));

        while (doc.getElementsByTagName("script").getLength() > 0) {
            NodeList scripts = doc.getElementsByTagName("script");

            for (int i = 0; i < scripts.getLength(); i++) {
                Element script = (Element) scripts.item(i);
                script.getParentNode().removeChild(script);
            }

        }


        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File("xmls/output" + System.currentTimeMillis() + ".xml"));
        Source input = new DOMSource(doc);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.transform(input, output);
        driver.quit();

    }

    private static String removeComments(String s) {
        while (s.indexOf("<!--") > 0) {
            int x = s.indexOf("<!--");
            int y = s.indexOf("-->");
            s = s.substring(0, x) + s.substring(y + "-->".length(), s.length());
        }
        return s;
    }

    private static String getBody(String s) {
        while (s.indexOf("<body") > 0) {
            int x = s.indexOf("<body");
            int y = s.indexOf("</body>");
            s = s.substring(x, y + "</body>".length());
        }
        return s;
    }

    private static String removeUnsupportedCharacters(String s) throws InterruptedException {

        s = s.replace("#", "");
        s = s.replace("&", "");
        s = s.replace("\uD83C\uDDF0\uD83C\uDDFC", "");

        return s;
    }
}
