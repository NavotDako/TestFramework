package mobile.Single.seetest;

import com.experitest.client.*;
import com.experitest.client.log.ILogger;
import com.experitest.client.log.Level;
import org.junit.*;
import utils.CloudServer;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;



public class gridST {

    protected Client client = null;

    @Before
    public void setUp() {
        CloudServer cs = new CloudServer(CloudServer.CloudServerNameEnum.QA);
        GridClient g = new GridClient(cs.ACCESSKEY, cs.HOST);
        client = g.lockDeviceForExecution("aaaa2", "@os='iOS'", true, 120, TimeUnit.MINUTES.toMillis(20));
        client.setReporter("xml", "reports", "Untitled");
    }

    @Test
    public void testUntitled() {
        // it is recommended to start your script with SetDevice command:
        client.swipeWhileNotFound("Down", 100, 100, "NATIVE", "//*[@id='no id']", 0, 1000, 100, true);
        client.deviceAction("Volume Up");

        client.install("C:\\Users\\DELL\\AppData\\Roaming\\seetest\\apk\\com.experitest.ExperiBank.LoginActivity.2.apk",false,false);
        client.setLogger(Utils.initDefaultLogger(Level.OFF));
        client.install("C:\\Users\\DELL\\AppData\\Roaming\\seetest\\apk\\com.experitest.ExperiBank.LoginActivity.2.apk",false,false);
        client.setLogger(Utils.initDefaultLogger(Level.INFO));
        client.install("C:\\Users\\DELL\\AppData\\Roaming\\seetest\\apk\\com.experitest.ExperiBank.LoginActivity.2.apk",false,false);


//        System.out.println(Arrays.toString(client.getDeviceSupportedLanguages()));
//        System.out.println(Arrays.toString(client.getDeviceSupportedRegions()));
//        client.setDeviceRegion("de_DE");
//        client.setDeviceLanguage("de-DE");
////
//        client.launch("https://www.bing.com/search?q=1", false, false);
//        client.setShowReport(false);
//        client.launch("https://www.bing.com/search?q=2", false, false);
//        client.setShowReport(true);
//        client.launch("https://www.bing.com/search?q=3", false, false);


    }

    @After
    public void tearDown() {
        client.generateReport(false);
        client.releaseClient();
    }
}