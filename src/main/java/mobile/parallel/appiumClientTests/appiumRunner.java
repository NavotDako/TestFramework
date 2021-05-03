package mobile.parallel.appiumClientTests;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import mobile.appiumDocker.appiumDockerRunner;
import mobile.parallel.appiumClientTests.tests.iOSNativeTest;
import mobile.parallel.appiumClientTests.tests.iOSWebTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import utils.CloudServer;
import utils.OS_Type;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static mobile.parallel.appiumClientTests.appiumRunner.cloudServer;
import static utils.Utilities.getTime;

public class appiumRunner {
    public static File report;
    public static CloudServer cloudServer;
    public static ConcurrentHashMap<String, String> map;
    public static int iterations = 10;
    private static int maxDevices = 5;
    private static FileWriter fw;

    public static void main(String[] args) throws InterruptedException {
        if(!createReport()) return;

        cloudServer = new CloudServer(CloudServer.CloudServerNameEnum.MINE);
        map = new ConcurrentHashMap<>();
        List<String> SNList = cloudServer.getAllAvailableDevices(OS_Type.iOS);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < SNList.size() && i < maxDevices; i++) {
            Loop loop = new Loop(SNList.get(i));
            Thread t = new Thread(loop);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        getSupportData();

//        for (int i = 0; i < deviceNum; i++) {
//            Loop loop = new Loop("");
//            Thread t = new Thread(loop);
//            t.start();
//        }

//        Loop loop = new Loop("7dab622e4213cc503ccca3fa4a213645d7f9e5da");
//        Thread t = new Thread(loop);
//        t.start();
        System.out.println("done");
    }

    public static boolean createReport() {
        report = new File("reports/report_" + System.currentTimeMillis() + ".log");
        String format = "%-5s%-30s%-50s%-50s%-10s%-10s%-20s%-15s%-100s%-250s";
        String resultFormatted = String.format(format, "I", "Time", "sn", "testClassName", "iter", "Status", "duration", "session", "report", "Failures");
        try {
            fw = new FileWriter(appiumRunner.report, true);
            fw.append(resultFormatted + "\n");
            fw.flush();
            fw.close();
            System.out.println("Report File Created - " + report.getName());
            return true;
        } catch (IOException e) {
            System.out.println("Report File WAS NOT Created - " + report.getName());
            e.printStackTrace();
        }
        return false;
    }

    private static void getSupportData() {
        System.out.println("START collect support data");
        GridClient gridClient = new GridClient(cloudServer.ACCESSKEY,cloudServer.HOST);
        Client client = gridClient.lockDeviceForExecution("collect","",5,300000);

        client.collectSupportData("reports_support_data","","","","","",true,false);
        client.releaseClient();
        System.out.println("END collect support data");

    }

}

class Loop implements Runnable {
    private final String sn;
    private final String os;
    private FileWriter fw;

    public Loop(String sn) {
        this.sn = sn;
        os = cloudServer.getDeviceOSByUDID(sn);
    }

    @Override
    public void run() {
        appiumRunner.map.put(Thread.currentThread().getName() + "_sn", sn);
        appiumRunner.map.put(Thread.currentThread().getName() + "_os", os);
        appiumRunner.map.put(Thread.currentThread().getName() + "_deviceQuery", "@serialnumber='" + sn + "'");

        Result result;

        for (int i = 1; i <= appiumRunner.iterations; i++) {
            System.out.println(getTime() + "\t" + Thread.currentThread().getName() + "\tStarting Iteration - " + i);
//            runTest(iOSNativeTest.class, i);
            runTest(iOSWebTest.class, i);
        }
    }

    private void runTest(Class testClass, int i) {
        JUnitCore junit = new JUnitCore();
        Result result;

        long start = System.currentTimeMillis();
        result = junit.run(testClass);
        long end = System.currentTimeMillis();

        if (result.getFailures().size() > 0) {
            result.getFailures().get(0).getException().printStackTrace();
        }

        String sid = appiumRunner.map.get(Thread.currentThread().getName() + "_sid");
        appiumRunner.map.remove(Thread.currentThread().getName() + "_sid");

        String report = appiumRunner.map.get(Thread.currentThread().getName() + "_report");
        appiumRunner.map.remove(Thread.currentThread().getName() + "_report");

        finishTest(result, i, iOSNativeTest.class.getSimpleName(), end - start, sid, report);
    }

    private synchronized void finishTest(Result result, int i, String testClassName, long duration, String sid, String report) {
        String resultFormatted;
        String format = "%-5s%-30s%-50s%-50s%-10s%-10s%-20s%-15s%-100s%-250s";
        if (result.getFailures().size() > 0) {
            resultFormatted = String.format(format, appiumDockerRunner.reportIndex + ".", getTime(), sn, testClassName, i, "Failure", ((int) duration / 1000) + "s", sid, report, result.getFailures().get(0).getMessage().replaceAll("\n", "\t"));
        } else {
            resultFormatted = String.format(format, appiumDockerRunner.reportIndex + ".", getTime(), sn, testClassName, i, "Success", ((int) duration / 1000) + "s", sid, report, "");
        }
        appiumDockerRunner.reportIndex++;
        printResult(resultFormatted);

    }

    public void printResult(String resultFormatted) {
        System.out.println(resultFormatted);
        try {
            fw = new FileWriter(appiumRunner.report, true);
            fw.append(resultFormatted + "\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
