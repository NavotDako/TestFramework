package mobile.Single.seetest;

import com.experitest.client.*;
import com.experitest.manager.api.ManagerPublisher;
import com.experitest.manager.client.PManager;
import com.experitest.manager.junit.ManagerTestRunner;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import utils.CloudServer;

import java.util.concurrent.TimeUnit;

//@RunWith(ManagerTestRunner.class)
public class TestManagerPublisher {
    protected Client client = null;
    protected GridClient gridClient = null;
    private ManagerPublisher mp = null;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
        CloudServer cloudServer = new CloudServer(CloudServer.CloudServerNameEnum.MINE);
        gridClient = new GridClient(cloudServer.ACCESSKEY, cloudServer.HOST);

        client = gridClient.lockDeviceForExecution("iOS 2", "@os='android'", 120, TimeUnit.MINUTES.toMillis(2));

        client.setReporter("xml", "reports", "DemoTestIOS");

        mp = gridClient.createPublisher(testName.getMethodName(), client);
        PManager.setPublisher(mp);
    }

    @Test
    public void testUntitled() {
        client.sleep(3000);
        mp.addProperty("CountryName", "France");
    }

    @After
    public void tearDown() {
        client.generateReport(false);
        client.releaseClient();
    }
}