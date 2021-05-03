package mobile.parallel;//package <set your test package>;

import utils.CloudServer;
import com.experitest.client.Client;
import com.experitest.client.GridClient;

/**
 *
 */
public class SeeTestTest implements Runnable {
    private static boolean parallel = true;
    protected Client client = null;
    protected GridClient grid = null;
    private boolean remote = true;
    private String serial;
    static CloudServer cloudServer;
    private String threadName;

    SeeTestTest(String serial) {
        this.serial = serial;
    }

    public static void main(String[] args) {
        parallel = false;
        cloudServer = new CloudServer(CloudServer.CloudServerNameEnum.RELEASE);
        SeeTestTest test = new SeeTestTest("e306fb3224fe2fbef2d1eb60118ee4ad7b7bf902");
        Thread t = new Thread(test);
        t.start();
    }

    @Override
    public void run() {
        threadName = Thread.currentThread().getName();
        if (parallel) {
            cloudServer = Parallel.cloudServer;
        }

        try {
            init();
        } catch (Exception e) {
            System.out.println(threadName + " - init failed");
            e.printStackTrace();
            return;
        }
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            finish();
        } catch (Exception e) {
            System.out.println(threadName + " - Finish failed");
            e.printStackTrace();
        }
    }

    private void init() {
        System.out.println(threadName + " - starting init");
        if (remote) {
            grid = new GridClient(cloudServer.ACCESSKEY, cloudServer.HOST);
//            grid.enableVideoRecording();

            client = grid.lockDeviceForExecution("Untitled", "@serialnumber='" + serial + "'", 100, 50000);
        } else {
            client = new Client("localhost", 8889, true);
            System.out.println("starting..");
            client.waitForDevice("@serialnumber='" + serial + "'", 300000);
        }
        client.setReporter("xml", "client:\\temp", "test1");
    }

    private void test() {

        System.out.println(threadName + " - starting test");
        System.out.println(client.getVisualDump("non-instrumented"));
        client.setProperty("safari.webview.include","true");
        System.out.println(client.getVisualDump("non-instrumented"));

        if (client.getDeviceProperty("device.os").equals("ANDROID")) {
//            client.install("C:\\Program Files (x86)\\Experitest\\SeeTest 12.1\\bin\\ipas\\eribank.apk", true, false);
//            client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        } else {
//            client.install("C:\\Program Files (x86)\\Experitest\\SeeTest 12.1\\bin\\ipas\\EriBank.ipa", true, false);
            client.launch("com.experitest.ExperiBank", true, true);
        }
//        client.elementSendText("native", "//*[@placeholder='Username']", 0, "ssesfsdf");
        String s = client.getDeviceLog();

        try {
            client.getRemoteFile(s,300000,"client:\\temp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void finish() {
        System.out.println(threadName + " - starting finish");
        client.generateReport(false);
        client.releaseClient();
    }

}
