package mobile;//package <set your test package>;

import com.experitest.client.*;

/**
 *
 */
public class localSeeTest implements Runnable{
    //    private final String serial;
    private String host = "localhost";
    private int port = 8889;
    private String projectBaseDirectory = "C:\\Users\\DELL\\workspace\\project2";
    protected Client client = null;

    public localSeeTest() {
//        this.serial = serial;

    }

    public void setUp() {
        client = new Client(host, port, true);
//        client.setProjectBaseDirectory(projectBaseDirectory);
        client.setReporter("xml", "reports", "sasxa");
    }


    public void testsasxa() {
        client.waitForDevice("", 10000);
        client.launch("http://www.google.com", true, true);            // If statement

    }

    public void tearDown() {
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        client.releaseClient();
    }

    @Override
    public void run() {
        setUp();
        testsasxa();
        tearDown();
    }
}
