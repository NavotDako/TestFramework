package mobile.Single;

import com.experitest.client.*;
import com.experitest.manager.api.ManagerPublisher;
import com.experitest.manager.client.PManager;
import com.experitest.manager.junit.ManagerTestRunner;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

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
        gridClient = new GridClient("eyJ4cC51IjoxLCJ4cC5wIjoyLCJ4cC5tIjoiTVRVMU1ERTFNekkyTURNNU1nIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NjU1MTMyNjEsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.s0_bzYrlAOIn8hujQH83eGk-3PxAzVC_p_ugThfi1x8", "http://192.168.2.13");
        client = gridClient.lockDeviceForExecution("iOS 2", "@os='ios'", 120, TimeUnit.MINUTES.toMillis(2));
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