package Utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    private static int index = 1;
    private static long time = System.currentTimeMillis();


    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss.SSS");
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
