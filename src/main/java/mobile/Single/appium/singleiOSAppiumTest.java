package mobile.Single.appium;

import com.experitest.appium.SeeTestClient;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.CloudServer;


import java.net.MalformedURLException;
import java.net.URL;

public class singleiOSAppiumTest {

    private String accessKey = "";
    protected IOSDriver<IOSElement> driver = null;
    protected SeeTestClient client;
    DesiredCapabilities dc = new DesiredCapabilities();

    private int defaultTimeout = 10000;
    private String usernameInputIos = "nixpath=//*[@class='UIATextField']";
    private String passwordInputIos = "nixpath=//*[@XCElementType='XCUIElementTypeSecureTextField']";
    private String loginButtonIos = "nixpath=//*[@text='Log in']";
    private String securityQuestionTextIos = "nixpath=//*[@XCElementType='XCUIElementTypeOther' and ./preceding-sibling::*[@id='Enhanced security requires you to answer the following question:']]";
    private String securityAnswerInputIos = "nixpath=//*[@placeholder='Enter answer']";
    private String submitButtonIos = "nixpath=//*[@id='Submit']";
    private String closeMfaDialogXButton = "//*[contains(@id, 'mfa-splash-modal-close')]";
    private String hamburgerMenu = "//*[@id='nav-menu-bar']";
    private String logoutLink = "nixpath=//*[@text='Logout']";
    private String paymentsAndTransferMenuButton = "//*[@text='Payments & Transfers']";
    private String depositChequeMenuButton = "//*[@id='nav-menu-rdc']";
    private String cameraIconButton = "//*[@id='camera-icon']";

    @Before
    public void setUp() throws MalformedURLException {
        CloudServer cs =  new CloudServer(CloudServer.CloudServerNameEnum.MINE);
        dc.setCapability("testName", "SimCapture");
        dc.setCapability("accessKey", cs.ACCESSKEY);
//        dc.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
        dc.setCapability("deviceQuery", "@serialnumber='b688eec7d140df3f6933c780a327d459cb5d29b7'");
        dc.setCapability("dontGoHomeOnQuit", true);
        driver = new IOSDriver<>(new URL(cs.gridURL), dc);
        client = new SeeTestClient(driver);
    }

    @Test
    public void quickStartiOSNativeDemo() throws InterruptedException {
        //Application launch
        client.install("cloud:com.atb.biz.banking.mobile", true, false);
        client.launch("cloud:com.atb.biz.banking.mobile", true, true);
        //Login
        client.waitForElement("NATIVE", usernameInputIos, 0, defaultTimeout);
        client.click("NATIVE", usernameInputIos, 0, 1);
        client.sendText("2472362");
        client.closeKeyboard();
        client.click("NATIVE", passwordInputIos, 0, 1);
        client.sendText("123456");
        client.closeKeyboard();
        client.click("NATIVE", loginButtonIos, 0, 1);
        //Security Question
        client.waitForElement("NATIVE", securityQuestionTextIos, 0, defaultTimeout);
        String answerIos = getSecurityAnswer(client.elementGetText("NATIVE", securityQuestionTextIos, 0));
        client.click("NATIVE", securityAnswerInputIos, 0, 1);
        client.sendText(answerIos);
        client.closeKeyboard();
        client.click("NATIVE", submitButtonIos, 0, 1);
        //Close MFA
        client.waitForElement("WEB", closeMfaDialogXButton, 0, defaultTimeout);
        //Navigate to RDC
        client.click("WEB", closeMfaDialogXButton, 0, 1);
        client.click("WEB", hamburgerMenu, 0, 1);
        client.waitForElement("NATIVE", logoutLink, 0, defaultTimeout);
        client.click("WEB", paymentsAndTransferMenuButton, 0, 1);
        client.waitForElement("WEB", depositChequeMenuButton, 0, 10000);
        client.click("WEB", depositChequeMenuButton, 0, 1);
        //Click camera icon and SimulateCapture
        client.waitForElement("WEB", cameraIconButton, 0, defaultTimeout);
        client.simulateCapture("bin/jpg.jpg");
        client.click("WEB", cameraIconButton, 0, 1);
        Thread.sleep(10000);
    }

    private String getSecurityAnswer(String securityQuestion) {
        String[] parts = securityQuestion.split(" ");
        String answer = parts[parts.length - 1];
        answer = answer.replace("?", "");
        return answer;
    }

    @After
    public void tearDown() {
        System.out.println("Report URL: " + driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
    }
}