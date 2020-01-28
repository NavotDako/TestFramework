package mobile.parallel.seetestClientTests;

import com.experitest.client.*;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 *
 */
public class SingleSeeTestGridTest {

    protected Client client = null;
    protected GridClient grid = null;

    @Before
    public void setUp() {
        grid = new GridClient(SingleRunner.cloudServer.ACCESSKEY, SingleRunner.cloudServer.gridURL);
        client = grid.lockDeviceForExecution("SingleSeeTestGridTest", SingleRunner.map.get(Thread.currentThread().getName()), 10, 600000);

        String sid = client.getSessionID();
        sid = sid.substring(sid.lastIndexOf("-") + 1);
        SingleRunner.map.put(Thread.currentThread().getName() + "_sid", sid);

        client.setReporter("xml", "reports", sid);
    }

    @Test
    public void quickStartiOSWebDemo() throws InterruptedException {
        client.setShowPassImageInReport(true);
        client.launch("http://192.168.2.170:8888/", true, true);

        for (int i = 0; i < 2; i++) {
            client.deviceAction("Landscape");
            client.deviceAction("Portrait");
        }
        client.install("cloud:com.experitest.ExperiBank", true, false);
        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        client.elementSendText("NATIVE", "hint=Username", 0, "company");
        client.elementSendText("NATIVE", "hint=Password", 0, "company");
        client.click("NATIVE", "text=Login", 0, 1);
        Thread.sleep(3000);
        client.click("NATIVE", "text=Make Payment", 0, 1);
        client.elementSendText("NATIVE", "hint=Phone", 0, "1234567");
        client.elementSendText("NATIVE", "hint=Name", 0, "Jon Snow");
        client.elementSendText("NATIVE", "hint=Amount", 0, "50");
//        client.click("NATIVE", "hint=Country", 0, 1);
        client.click("NATIVE", "text=Select", 0, 1);
        client.click("NATIVE", "text=Switzerland", 0, 1);
        Thread.sleep(3000);
        client.click("NATIVE", "text=Send Payment", 0, 1);
        client.click("NATIVE", "text=Yes", 0, 1);
        client.click("NATIVE", "text=Logout", 0, 1);
    }

    @After
    public void tearDown() {

        String report = null;

        try {
            report = client.generateReport(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SingleRunner.map.put(Thread.currentThread().getName() + "_report", report);
        client.releaseClient();
    }

    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            try {
                if (client != null) {
                    System.out.println(client.getVisualDump("native"));
                    System.out.println(client.getVisualDump("web"));
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }

        @Override
        protected void succeeded(Description description) {

        }
    };
}


