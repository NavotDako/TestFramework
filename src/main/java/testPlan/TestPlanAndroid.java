package testPlan;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;

public class TestPlanAndroid {
    public static void main(String[] args) throws UnirestException {
        File app = new File("lib/Tests/app-debug.apk");
        File testApp =  new File("lib/Tests/app-debug-androidTest.apk");

        HttpResponse<String> response = Unirest.post("http://192.168.2.13/api/v1/test-run/execute-test-run-async")
                .basicAuth("admin", "Experitest2012")
                .queryString("executionType", "espresso")
                .queryString("runningType", "coverage")
                .queryString("deviceQueries", "@os='android',@os='android',@os='android',@os='android'"/*,@os='android',@os='android'*/)
                .field("app", app)
                .field("testApp", testApp)
                .asString();

        System.out.println(response.getBody());
    }
}
