package Utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;

public class uploadAppToCloud {
    public static void main(String[] args) {
        File f = new File("C:\\Users\\DELL\\Downloads\\com.chase.pay.test_ver_4186.ipa");
        String appName = "Eri"+ System.currentTimeMillis();
        HttpResponse<String> response = null;
        Unirest.setTimeouts(60*1000*5, 60*1000*5);

        try {
            response = Unirest.post("http://192.168.2.13" + "/api/v1/applications/new")
                    .basicAuth ("admin", "Experitest2012")
                    .queryString("project", /*"id:"+*/"default") // mandatory for Cloud Administrator
                    .queryString("uniqueName", "unique") // optional field
                    .field ("file", f) // mandatory field
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        System.out.println(response.getBody());
        System.out.println(appName);
    }
}
