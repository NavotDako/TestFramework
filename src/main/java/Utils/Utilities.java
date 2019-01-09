package Utils;

import somepack.Parallel;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    private static int index = 1;
    private static long time = System.currentTimeMillis();


    public static String getTime() {
        return Parallel.sdf.format(new Date(System.currentTimeMillis()))+":";
    }

    public static String getTimeForFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss-SSS");
        return sdf.format(new Date(System.currentTimeMillis()));
    }
    public static synchronized void log(String message) throws IOException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("reports/report_"+time, true)));
        writer.write(index + ". " + message + "\n");
        index++;
        writer.flush();
        writer.close();
    }
}
