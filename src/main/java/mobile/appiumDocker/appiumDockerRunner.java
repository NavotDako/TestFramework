package mobile.appiumDocker;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static utils.Utilities.getTime;

public class appiumDockerRunner {
    public static List<String> deviceList;
    public static Map<String, String> map;
    public static File report;
    public static int reportIndex = 1;
    public static String cloud = "http://192.168.2.170/wd/hub";
    public static String accessKey = "eyJ4cC51Ijo4NywieHAucCI6MSwieHAubSI6Ik1UVTFPVFkwTlRJeE5qazVNZyIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4NzUwMDUyMTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.ysVIGIHlB1O4XCuZQ6dD5UamjbXazbSrH-AD6DdrIhw";
    public static boolean OSS = true;

    public static void main(String[] args) {
        report = new File("reports/report_" + System.currentTimeMillis() + ".log");
        System.out.println("Starting Execution. Report - " + report.getName());

        deviceList = new ArrayList<>();
        deviceList.add("d7fae3c92a1aa36ce7bebc5ecd560ea6322d9654");
//        deviceList.add("b6f5298f60050d4f7697a068258c6eb52b73c5f6");
        deviceList.add("ad0e395a69cf5efb5bfab8069d69d5d30302a6d2");
        deviceList.add("bb904d305dad81b8ae67386753143a28fe81cf1b");

//        deviceList.add("840cb0353d2dcfeea77a5cd6bd8f478e73f131a3");

        map = new ConcurrentHashMap<>();
        for (int i = 0; i < deviceList.size(); i++) {
            Thread t = new Thread(new Looper(deviceList.get(i)));
            t.start();
        }
    }


}

class Looper extends Thread {

    private final String sn;
    private FileWriter fw;

    public Looper(String sn) {
        this.sn = sn;
    }

    @Override
    public void run() {
        System.out.println(getTime() + "\t" + Thread.currentThread().getName() + "\t" + "Starting Looper - " + sn);
        appiumDockerRunner.map.put(Thread.currentThread().getName(), sn);

        List<Class<?>> testList = new ArrayList<>();
        testList.add(appiumDockerWeb.class);
//        testList.add(appiumDockerNative.class);

        JUnitCore junit = new JUnitCore();
        Result result;
        for (int i = 1; i < 1000; i++) {
            System.out.println(getTime() + "\t" + Thread.currentThread().getName() + "\tStarting Iteration - " + i);
            for (Class<?> testClass : testList) {
                long start = System.currentTimeMillis();
                result = junit.run(testClass);
                long end = System.currentTimeMillis();
                if (result.getFailures().size() > 0) {
                    result.getFailures().get(0).getException().printStackTrace();
                }
                finishTest(result, i, testClass.getName(), end - start);
                sleep(10000);
            }
        }
    }

    private synchronized void finishTest(Result result, int i, String testClassName, long duration) {
        String resultFormatted;
        String format = "%-5s%-30s%-50s%-50s%-10s%-20s%-250s";
        if (result.getFailures().size() > 0) {
            resultFormatted = String.format(format, appiumDockerRunner.reportIndex + ".", getTime(), sn, testClassName, "Failure", ((int) duration / 1000) + "s", result.getFailures().get(0).getMessage().replaceAll("\n", "\t"));
        } else {
            resultFormatted = String.format(format, appiumDockerRunner.reportIndex + ".", getTime(), sn, testClassName, "Success", ((int) duration / 1000) + "s", "");
        }
        appiumDockerRunner.reportIndex++;
        printResult(resultFormatted);

    }

    private synchronized void printResult(String resultFormatted) {

        System.out.println(resultFormatted);
        try {
            fw = new FileWriter(appiumDockerRunner.report, true);
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
