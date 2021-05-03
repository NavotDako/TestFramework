package mobile.parallel.seetestClientTests;

import mobile.appiumDocker.appiumDockerRunner;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import utils.CloudServer;
import utils.OS_Type;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static utils.Utilities.getTime;

public class SingleRunner {
    public static File report;
    public static CloudServer cloudServer;
    public static ConcurrentHashMap<String, String> map;
    static int deviceNum = 3;
    public static int iterations = 10;

    public static void main(String[] args) {
        report = new File("reports/report_" + System.currentTimeMillis() + ".log");
        cloudServer = new CloudServer(CloudServer.CloudServerNameEnum.MINE);
        map = new ConcurrentHashMap<>();

        List<String> SNList = GetAllSNFromCloud(cloudServer);
        for (int i = 0; i < SNList.size(); i++) {
            Loop loop = new Loop("@serialnumber='"+SNList.get(i)+"'");
            Thread t = new Thread(loop);
            t.start();
        }
//
//        for (int i = 0; i < deviceNum; i++) {
//            Loop loop = new Loop("@serialnumber='" + "" + "'");
//            Thread t = new Thread(loop);
//            t.start();
//        }

//        for (int i = 0; i < deviceNum; i++) {
//            Loop loop = new Loop("@emulator='true' and @version='9.0'");
//            Thread t = new Thread(loop);
//            t.start();
//        }

    }

    private static List<String> GetAllSNFromCloud(CloudServer cloudServer) {

        return cloudServer.getAllAvailableDevices(OS_Type.ALL);
    }
}

class Loop implements Runnable {
    private final String deviceQuery;
    private FileWriter fw;


    public Loop(String deviceQuery) {
        this.deviceQuery = deviceQuery;
    }

    @Override
    public void run() {

        SingleRunner.map.put(Thread.currentThread().getName(), deviceQuery);


        JUnitCore junit = new JUnitCore();
        Result result;

        for (int i = 1; i <= SingleRunner.iterations; i++) {
            System.out.println(getTime() + "\t" + Thread.currentThread().getName() + "\t" + "Starting Iteration - " + i);
            long start = System.currentTimeMillis();
            result = junit.run(SingleSeeTestGridTest.class);
            long end = System.currentTimeMillis();

            if (result.getFailures().size() > 0) {
                result.getFailures().get(0).getException().printStackTrace();
            }

            String sid = SingleRunner.map.get(Thread.currentThread().getName() + "_sid");
            SingleRunner.map.remove(Thread.currentThread().getName() + "_sid");

            String report = SingleRunner.map.get(Thread.currentThread().getName() + "_report");
            SingleRunner.map.remove(Thread.currentThread().getName() + "_report");

            finishTest(result, i, SingleSeeTestGridTest.class.getName(), end - start, sid, report);
            sleep(1000);
        }
    }

    private synchronized void finishTest(Result result, int i, String testClassName, long duration, String sid, String report) {
        String resultFormatted;
        String format = "%-5s%-30s%-50s%-50s%-10s%-10s%-20s%-15s%-100s%-250s";
        if (result.getFailures().size() > 0) {
            resultFormatted = String.format(format, appiumDockerRunner.reportIndex + ".", getTime(), deviceQuery, testClassName, i, "Failure", ((int) duration / 1000) + "s", sid, report, result.getFailures().get(0).getMessage().replaceAll("\n", "\t"));
        } else {
            resultFormatted = String.format(format, appiumDockerRunner.reportIndex + ".", getTime(), deviceQuery, testClassName, i, "Success", ((int) duration / 1000) + "s", sid, report, "");
        }
        appiumDockerRunner.reportIndex++;
        printResult(resultFormatted);

    }

    private void printResult(String resultFormatted) {
        System.out.println(resultFormatted);
        try {
            fw = new FileWriter(SingleRunner.report, true);
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
