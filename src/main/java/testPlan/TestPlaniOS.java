package testPlan;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;

public class TestPlaniOS {
    public static void main(String[] args) throws UnirestException {
        File app = new File("lib/Tests/PressTheDot093.ipa");
        File testApp =  new File("lib/Tests/PressTheDotUITests-Runner093.app.zip");

        HttpResponse<String> response = Unirest.post("http://192.168.2.13/api/v1/test-run/execute-test-run")
                .basicAuth("admin", "Experitest2012")
                .queryString("executionType", "xcuitest")
                .queryString("runningType", "coverage")
                .queryString("deviceQueries", "@os='ios'")
                .queryString("creationTimeout", "10000")
                .field("app", app)
                .field("testApp", testApp)
                .asString();
        System.out.println(response.getBody());
    }
}
