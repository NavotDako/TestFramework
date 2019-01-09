package Utils;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CloudServer {
    private static String webPage;
    private static String authStringEnc;

    public String HOST;
    private String PORT;
    private String USER;
    private String PASS;
    private String PROJECT;
    private boolean SECURED;
    public String ACCESSKEY = null;

    public String gridURL;

    private CloudServerNameEnum cloudName;

    private String result;

    public CloudServer(CloudServerNameEnum cloudName) {
        System.out.println("Initiating The Cloud Object");
        this.cloudName = cloudName;
        updateCloudDetails();
        String prefix = "http://";
        if (HOST.contains("https")) prefix = "";
        gridURL = prefix + this.HOST + ":" + this.PORT + "/wd/hub";
        String authString = this.USER + ":" + this.PASS;
        webPage = prefix + this.HOST + ":" + this.PORT + "/api/v1";
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        authStringEnc = new String(authEncBytes);
        System.out.println("Done Initiating The Cloud Object");
        System.out.println("Cloud Details:\n" + this.toString());
        try {
            String DEVICES_URL = "/devices";
            result = doGet(DEVICES_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection to Cloud IP - " + HOST + " - Is OK");
    }

    public String toString() {
        return (String.format("%-20s\n%-10s\n%-20s\n", "HOST - " + HOST, "PORT - " + PORT, "USER - " + USER));
    }

    public enum CloudServerNameEnum {
        MINE, QA, RELEASE, DIKLA, DEEP, ESXI, ATB, SHAHAR
    }

    private void updateCloudDetails() {

        switch (cloudName) {
            case SHAHAR:
                HOST = "192.168.2.126";
                PORT = "80";
                USER = "admin2";
                PASS = "Experitest2012";
                PROJECT = "default";
                SECURED = false;
                ACCESSKEY = "eyJ4cC51Ijo0LCJ4cC5wIjoyLCJ4cC5tIjoiTVRVME5qZzBOalUzTnpFME53IiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NjIyMDY1NzcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.7bPATsFfIsPmYEXMZuvAHwsLk27owJaJ03nt0QtcWj8";
                break;
            case MINE:
                HOST = "192.168.2.13";
                PORT = "80";
                USER = "admin";
                PASS = "Experitest2012";
                PROJECT = "Default";
                SECURED = false;
                ACCESSKEY = "eyJ4cC51IjoxLCJ4cC5wIjoyLCJ4cC5tIjoiTVRVME16a3hPRFl3T1RVeE1RIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NTkyNzg2MTAsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.tFnvZGwN7_qNl9MJltbbIUFxXdtIKKG9SR0vrfYXqAE";
                break;
            case ATB:
                HOST = "https://atb.experitest.com";
                PORT = "443";
                USER = "amit.nahum";
                PASS = "Zz123456";
                PROJECT = "Default";
                SECURED = false;
                ACCESSKEY = " eyJ4cC51IjoxMDI5ODQsInhwLnAiOjIsInhwLm0iOiJNQSIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4NTY5NjA4NzQsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.YBWxHU0OUQIFNTa0J9rGdZz2vmqRI-lvhHfxbcmHyrY";
                break;
            case ESXI:
                HOST = "https://rndvmcloud.experitest.com";
                PORT = "443";
                USER = "rndcloud";
                PASS = "Rnd2018*";
                PROJECT = "Default";
                SECURED = false;
                ACCESSKEY = "eyJ4cC51Ijo3LCJ4cC5wIjoyLCJ4cC5tIjoiTVRVek9UWTNORGMxTURRMk1nIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NTUwMzQ3NTAsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.EoVOoXLYA68RcIaBwT4KmV8uKYavfZHc9id9e0RCwgU";
                break;
            case DEEP:
                HOST = "https://qa-win2016.experitest.com";
                PORT = "443";
                USER = "diklapa";
                PASS = "Aa123456";
                PROJECT = "CCB-Digital";
                ACCESSKEY = "eyJ4cC51Ijo1ODI1MDM5LCJ4cC5wIjo1LCJ4cC5tIjoiTVRVME1qVXpNRGMyT0RRM09RIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NTc4OTA3NjgsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.5okoa6bUNe5ib5zwMwqRPRsvsVH6TGxV5RBxsLVRgME";
                SECURED = true;
                break;
            case DIKLA:
                HOST = "192.168.1.59";
                PORT = "8090";
                USER = "admin";
                PASS = "Experitest2012";
                break;
            case QA:
                HOST = "https://qacloud.experitest.com";
                PORT = "443";
                USER = "eyal";
                PASS = "Experitest2012";
                SECURED = true;
                ACCESSKEY = "eyJ4cC51IjoyMTc2LCJ4cC5wIjoyLCJ4cC5tIjoiTVRRNU9USXpPRE0zTkRrek9RIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjQ2ODIzOTY1OTAsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.lymE9fDIIlTLsrr-ph6oMlgtpVY7-knj44SIZXc1rMc";
                break;
            case RELEASE:
                HOST = "https://releasecloud.experitest.com";
                PORT = "443";
                USER = "eyal";
                PASS = "Experitest2012";
                SECURED = true;
                ACCESSKEY = "eyJ4cC51IjoyMTc2LCJ4cC5wIjoyLCJ4cC5tIjoiTVRRNU9USXpPRE0zTkRrek9RIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjQ2NzIxMzI4NTQsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.na315fDIXdpqkRVem7V1vbiHnST4I7Ee3vYMIpBb11A";
                break;
        }
    }

    private String doGet(String entity) throws IOException {
        URL url = new URL(webPage + entity);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        InputStream is = urlConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        int numCharsRead;
        char[] charArray = new char[1024];
        StringBuilder sb = new StringBuilder();
        while ((numCharsRead = isr.read(charArray)) > 0) {
            sb.append(charArray, 0, numCharsRead);
        }
        String result = sb.toString();
        if (((HttpURLConnection) urlConnection).getResponseCode() < 300) {
            return result;
        } else {
            throw new RuntimeException(result);
        }
    }

    public String getDeviceOSByUDID(String UDID) {
        return getDeviceOS(result, UDID);
    }

    private String getDeviceOS(String result, String udid) {
        JSONObject jsonObject = new JSONObject(result);
        Map obj = jsonObject.toMap();
        List<Object> data = (List<Object>) obj.get("data");
        Object[] devicesArray = data
                .stream()
                .filter(student -> ((Map) student).get("udid").equals(udid))
                .toArray();

        String[] devicePropertiesArray = devicesArray[0].toString().replace("{", "").replace("]", "").split(",");
        int j = 0;

        boolean udidFlag = false;
        boolean osFlag = false;
        String deviceOs = null;
        while (j < devicePropertiesArray.length && (!udidFlag || !osFlag)) {

            if (devicePropertiesArray[j].contains("deviceOs")) {
                deviceOs = devicePropertiesArray[j].replace("deviceOs=", "").trim().toLowerCase();
                osFlag = true;
            }
            j++;
        }
        return deviceOs;
    }

    public List<String> getAllAvailableDevices(String os) {

        List<String> devicesList = getAvailableDevicesList(result, os);
        return devicesList;
    }

    private List<String> getAvailableDevicesList(String result, String os) {
        List<String> tempDevicesList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(result);
        Map obj = jsonObject.toMap();
        List<Object> data = (List<Object>) obj.get("data");
        Object[] devicesArray = GetFilteredDevices(data, os);

        for (int i = 0; i < devicesArray.length; i++) {
            String[] devicePropertiesArray = devicesArray[i].toString().replace("{", "").replace("]", "").split(",");
            int j = 0;

            boolean udidFlag = false;
            String udid = null;

            while (j < devicePropertiesArray.length && !udidFlag) {
                if (devicePropertiesArray[j].contains("udid")) {
                    udid = devicePropertiesArray[j].replace("udid=", "").trim();
                    udidFlag = true;
                }
                j++;
            }
            tempDevicesList.add(udid);
        }

        System.out.println("DevicesList size - " + tempDevicesList.size() + " - " + tempDevicesList.toString());
        return tempDevicesList;
    }

    private Object[] GetFilteredDevices(List<Object> data, String os) {
        Object[] devicesArray = new Object[0];
        switch (os) {
            case "android": {
                devicesArray = data
                        .stream()
                        .filter(device -> ((Map) device).get("displayStatus").equals("Available") && ((Map) device).get("deviceOs").equals("Android"))
                        .toArray();
                break;
            }
            case "ios": {
                devicesArray = data
                        .stream()
                        .filter(device -> ((Map) device).get("displayStatus").equals("Available") && ((Map) device).get("deviceOs").equals("iOS"))
                        .toArray();
                break;
            }
            case "all": {
                devicesArray = data
                        .stream()
                        .filter(device -> ((Map) device).get("displayStatus").equals("Available"))
                        .toArray();
                break;
            }
        }
        return devicesArray;
    }
}
