package somepack.Single;

import com.experitest.client.*;
import org.junit.*;
/**
 *
 */
public class SingleSTATest {
    private String host = "localhost";
    private int port = 8889;
    private String projectBaseDirectory = "C:\\Users\\amit.nahum\\workspace\\project9";
    protected Client client = null;

    @Before
    public void setUp(){
        client = new Client(host, port, true);
//        client.setProjectBaseDirectory(projectBaseDirectory);
        client.setReporter("xml", "reports", "Untitled");
    }

    @Test
    public void testUntitled(){
        // it is recommended to start your script with SetDevice command:
        client.setDevice("adb:HUAWEI MT7-L09");
    }

    @After
    public void tearDown(){
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        client.releaseClient();
    }
}