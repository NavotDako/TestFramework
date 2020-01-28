package mobile.parallel;


import utils.CloudServer;
import utils.Utilities;
import utils.OS_Type;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Parallel {

    static CloudServer cloudServer;
    static boolean appium = true;
    private static boolean localSeeTestBool = false;
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    static int MAX = 60;
    static int CURRENT = 0;

    public static void main(String[] args) throws InterruptedException {
        cloudServer = new CloudServer(CloudServer.CloudServerNameEnum.MINE);
        List<String> devicesList = cloudServer.getAllAvailableDevices(OS_Type.ALL);

        ExecutorService executorService = Executors.newFixedThreadPool(devicesList.size());
        List<Future> res = new LinkedList<>();

//        MAX = devicesList.size();// 2;
        System.out.println("Max Parallel - " + MAX);

//        for (int i = 0; i < devicesList.size(); i++) {
//            if (localSeeTestBool) {
//                localSeeTest s = new localSeeTest();
//                Thread t = new Thread(s);
//                t.start();
//            } else {
//                if (appium) {
//                    loop l = new loop(devicesList.get(i));
//                    res.add(executorService.submit(l));
//                } else {
//                    SeeTestTest test = new SeeTestTest(devicesList.get(i));
//                    res.add(executorService.submit(test));
//                }
//            }
//        }
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
        System.out.println(Utilities.getTime() + "\t" + serial + " - Starting Loop Thread");

        for (int i = 0; i < 1000; i++) {

            sleep(2000);
            boolean waitBool = true;
            while (waitBool) {
                synchronized (Parallel.cloudServer) {
                    if (Parallel.CURRENT < Parallel.MAX) {
                        System.out.println(Utilities.getTime() + "\t" + serial + " - Adding To Current - " + Parallel.CURRENT);
                        Parallel.CURRENT++;
                        System.out.println(Utilities.getTime() + "\t" + serial + " - Added To Current - " + Parallel.CURRENT);
                        waitBool = false;
                    }
                }
                if (waitBool) {
                    sleep(500);
                }
            }
            start(i);
            releaseLock();
        }
    }

    private void start(int i) {
        AppiumTest test = new AppiumTest(serial, i);
        test.run();
    }

    private void releaseLock() {
        synchronized (Parallel.cloudServer) {
            System.out.println(Utilities.getTime() + "\t" + Thread.currentThread().getName() + " - Removing From Current - " + Parallel.CURRENT);
            Parallel.CURRENT--;
            System.out.println(Utilities.getTime() + "\t" + Thread.currentThread().getName() + " - Removed From Current - " + Parallel.CURRENT);
        }
    }

    private void sleep(int waitMilisec) {
        try {
            Thread.sleep(waitMilisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

