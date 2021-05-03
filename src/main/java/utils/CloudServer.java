package utils;

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

    public String getDeviceVersion(String udid) {
        String[] devicePropertiesArray = getDeviceDetails(udid);
        String deviceVersion = getDeviceProperty(devicePropertiesArray, "osVersion");
        return deviceVersion;
    }

    public enum CloudServerNameEnum {
        MINE, QA, RELEASE, DIKLA, DEEP, ESXI, ATB, SHAHAR, MASTER, DBS, LIRAN, SALES, ORTAL
    }

    private void updateCloudDetails() {

        switch (cloudName) {

            case ORTAL:
                HOST = "192.168.2.191";
                PORT = "80";
                USER = "admin";
                PASS = "Aa123456";
                PROJECT = "default";
                SECURED = false;
                ACCESSKEY = "eyJ4cC51IjoxLCJ4cC5wIjoxLCJ4cC5tIjoiTVRVMk16STNPVGM1TlRrNE5BIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4Nzg2Mzk3OTYsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.DRYshGw9gvgzpQXRL5SGCqiz3l7Q1KTcQCJgyhwb0_Q";
                break;
            case SALES:
                HOST = "https://sales.experitest.com";
                PORT = "443";
                USER = "eyal";
                PASS = "Experitest2012";
                PROJECT = "default";
                SECURED = true;
                ACCESSKEY = "eyJ4cC51IjoyMDQwMzEsInhwLnAiOjIsInhwLm0iOiJNVFUwTVRNME5qWTNPREU1T0EiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4NTY3MDY2NzgsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.B0tPNwQNsRKyYNDkcJ4bfVNvjweYEGZGhvTMc1Ygo7I";
                break;
            case LIRAN:
                HOST = "192.168.2.31";
                PORT = "80";
                USER = "admin";
                PASS = "Experitest2012";
                PROJECT = "default";
                SECURED = true;
                ACCESSKEY = "eyJ4cC51IjoxLCJ4cC5wIjoyLCJ4cC5tIjoiTVRVd016SXhNekF3TURFd01BIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4MjcyMzQyNzQsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.mpN_KLwp4bml3UvEZoxg2Sn-GRLwkTcQEL5G4U5N3nk";
                break;
            case DBS:
                HOST = "https://dbs-sg.experitest.com";
                PORT = "443";
                USER = "omer";
                PASS = "Experitest2012";
                PROJECT = "default";
                SECURED = true;
                ACCESSKEY = "eyJ4cC51Ijo0OTQ3MTEsInhwLnAiOjExMjk3MSwieHAubSI6Ik1UVTFNamt3TmpBeU9EWTFPUSIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4NjgyNjYwMjgsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.34PsG1Ow-_Uc4QJ9id2K-CaU6fL2F0WhRBTixRj5dM8";
                break;
            case MASTER:
                HOST = "https://mastercloud.experitest.com";
                PORT = "443";
                USER = "eyal";
                PASS = "Experitest2012";
                PROJECT = "default";
                SECURED = true;
                ACCESSKEY = "eyJ4cC51Ijo3LCJ4cC5wIjoyLCJ4cC5tIjoiTVRVME1qVTFNREU0TWpRNE9RIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NTc5Nzc2NjYsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.bk67gFsjtczZbRMvjxnowEFBi8yhx6YCeIWwIwWkqTI";
                break;
            case SHAHAR:
                HOST = "192.168.2.126";
                PORT = "80";
                USER = "admin";
                PASS = "Aa123456";
                PROJECT = "default";
                SECURED = false;
                ACCESSKEY = "eyJ4cC51IjoxLCJ4cC5wIjoyLCJ4cC5tIjoiTVRVMU1UWTROamt4TlRVd05RIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NjcwNDY5MTYsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.4kQVexJ5eD-tXxlbeKWWA_oVnBOBUmUSyxORKCoOhKA";
                break;
            case MINE:
                HOST = "192.168.2.170";
                PORT = "80";
                USER = "admin";
                PASS = "Experitest2012";
                PROJECT = "Default";
                SECURED = false;
                ACCESSKEY = "eyJ4cC51Ijo4NywieHAucCI6MSwieHAubSI6Ik1UVTFPVFkwTlRJeE5qazVNZyIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4NzUwMDUyMTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.ysVIGIHlB1O4XCuZQ6dD5UamjbXazbSrH-AD6DdrIhw";
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
                HOST = "https://qa-deep.experitest.com";
                PORT = "443";
                USER = "navot";
                PASS = "Experitest2012";
                PROJECT = "default";
                ACCESSKEY = "eyJ4cC51Ijo0MTM1MTMwNCwieHAucCI6MiwieHAubSI6Ik1UVTNOREkxTXpZMU5UWTVNZyIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4ODk2MTM2NTUsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.qsimPnbskdSGt_n4Y8WbMQ3NUpVhIEn6yWcqWTSVuz8";
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
        return getDeviceOS(UDID);
    }

    private String getDeviceOS(String udid) {
        String[] devicePropertiesArray = getDeviceDetails(udid);
        String deviceOs = getDeviceProperty(devicePropertiesArray, "deviceOs");
        return deviceOs;
    }

    private String getDeviceProperty(String[] devicePropertiesArray, String deviceProperty) {
        int j = 0;
        String property = null;
        while (j < devicePropertiesArray.length) {
            if (devicePropertiesArray[j].contains(deviceProperty)) {
                property = devicePropertiesArray[j].replace(deviceProperty + "=", "").trim().toLowerCase();
                return property;
            }
            j++;
        }
        return property;
    }

    private String[] getDeviceDetails(String udid) {
        JSONObject jsonObject = new JSONObject(result);
        Map obj = jsonObject.toMap();
        List<Object> data = (List<Object>) obj.get("data");
        Object[] devicesDetailsObjectsArray = data
                .stream()
                .filter(student -> ((Map) student).get("udid").equals(udid))
                .toArray();

        String[] devicesDetailsArray = devicesDetailsObjectsArray[0].toString().replace("{","").replace("}","").split(",");

        return devicesDetailsArray;
    }

    public List<String> getAllAvailableDevices(OS_Type enumOs) {

        List<String> devicesList = getAvailableDevicesList(result, enumOs);
        return devicesList;
    }

    private List<String> getAvailableDevicesList(String result, OS_Type enumOs) {
        List<String> tempDevicesList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(result);
        Map obj = jsonObject.toMap();
        List<Object> data = (List<Object>) obj.get("data");
        Object[] devicesArray = GetFilteredDevices(data, enumOs);

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

    private Object[] GetFilteredDevices(List<Object> data, OS_Type enumOs) {
        Object[] devicesArray = new Object[0];
        switch (enumOs) {
            case Android: {
                devicesArray = data
                        .stream()
                        .filter(device -> (((Map) device).get("displayStatus").equals("In Use") || ((Map) device).get("displayStatus").equals("Available")) && ((Map) device).get("deviceOs").equals("Android"))
                        .toArray();
                break;
            }
            case iOS: {
                devicesArray = data
                        .stream()
                        .filter(device -> (((Map) device).get("displayStatus").equals("In Use") || ((Map) device).get("displayStatus").equals("Available")) && ((Map) device).get("deviceOs").equals("iOS"))
                        .toArray();
                break;
            }
            case ALL: {
                devicesArray = data
                        .stream()
                        .filter(device -> (((Map) device).get("displayStatus").equals("In Use") || ((Map) device).get("displayStatus").equals("Available")))
                        .toArray();
                break;
            }
        }
        return devicesArray;
    }
}
