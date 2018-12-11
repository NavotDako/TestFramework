package somepack;


import Utils.CloudServer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Parallel {

    static CloudServer cloudServer;
    static boolean appium = true;

    public static void main(String[] args) {

        cloudServer = new CloudServer(CloudServer.CloudServerNameEnum.MINE);
        List<String> devicesList = cloudServer.getAllAvailableDevices("all");

        ExecutorService executorService = Executors.newFixedThreadPool(devicesList.size());
        List<Future> res = new LinkedList<>();

        for (int i = 0; i < devicesList.size(); i++) {
            if (appium) {
                loop l = new loop(devicesList.get(i));
                res.add(executorService.submit(l));
            } else {
                SeeTestTest test = new SeeTestTest("");
                res.add(executorService.submit(test));
            }
        }
        System.out.println("Starting All Threads");

        for (Future re : res) {
            try {
                re.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        System.out.println("Done With All Tests");

    }
}

class loop implements Runnable {
    private final String serial;

    public loop(String serial) {
        this.serial = serial;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            AppiumTest test = new AppiumTest(serial , i);
            test.run();

        }
    }
}