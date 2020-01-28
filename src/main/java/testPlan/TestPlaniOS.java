package testPlan;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;

public class TestPlaniOS {
    public static void main(String[] args) throws UnirestException {
        File app = new File("bin/RebankPreProd (f4f760b8-218f-40de-81cf-1eb81a713b20).ipa");
        File testApp =  new File("bin/RebankTestApp.zip");

        HttpResponse<String> response = Unirest.post("http://192.168.2.170/api/v1/test-run/execute-test-run")
                .basicAuth("admin", "Experitest2012")
                .queryString("executionType", "xcuitest")
                .queryString("runningType", "coverage")
                .queryString("deviceQueries", "@os='ios' and serialnumber='00008020-000844AC3408002E'")
                .queryString("creationTimeout", "10000")
                .field("app", app)
                .field("testApp", testApp)
                .asString();
        System.out.println(response.getBody());
    }
}
