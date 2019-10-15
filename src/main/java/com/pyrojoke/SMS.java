package com.pyrojoke;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.json.JSONObject;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class SMS {

    public static String getSmsCode(String telephoneNumber){
        RestAssured.baseURI = "URL";
        JSONObject logPass = new JSONObject();
        logPass.put("login", "login");
        logPass.put("password", "password");
        Map<String, String> cookies = given().contentType(ContentType.JSON).body(logPass.toString()).when().get("/").getCookies();
        String JSESSIONID=given().contentType(ContentType.JSON).body(logPass.toString()).when().post("/").getHeaders().asList().get(2).getValue().split(";")[0].split("=")[1];
        boolean flag = true;
        String lastMessage = "";
        while(flag) {
            JsonPath response = given().header("Cookie", "XSRF-TOKEN=" + cookies.get("XSRF-TOKEN") + "; JSESSIONID=" + JSESSIONID)
                    .header("Referer", "")
                    .queryParam("address", telephoneNumber).queryParam("sort", "createdAt,desc")
                    .when().get("").jsonPath();
            lastMessage = response.get("content[0].message");
            System.out.println(lastMessage);
            if(lastMessage!=null)
                if(lastMessage.contains("Код для подтверждения номера телефона"))
                    flag = false;
        }
        return lastMessage.split(" ")[5].replace(".","");

    }

    public static String getSmsCodeForRestorePassword(String telephoneNumber){
        RestAssured.baseURI = "URL";
        JSONObject logPass = new JSONObject();
        logPass.put("login", "login");
        logPass.put("password", "password");
        Map<String, String> cookies = given().contentType(ContentType.JSON).body(logPass.toString()).when().get("/").getCookies();
        String JSESSIONID=given().contentType(ContentType.JSON).body(logPass.toString()).when().post("/").getHeaders().asList().get(2).getValue().split(";")[0].split("=")[1];
        boolean flag = true;
        String lastMessage = "";
        while(flag) {
            JsonPath response = given().header("Cookie", "XSRF-TOKEN=" + cookies.get("XSRF-TOKEN") + "; JSESSIONID=" + JSESSIONID)
                    .header("Referer", "")
                    .queryParam("address", telephoneNumber).queryParam("sort", "createdAt,desc")
                    .when().get("/").jsonPath();
            lastMessage = response.get("content[0].message");
            System.out.println(lastMessage);
            if(lastMessage!=null)
                if(lastMessage.contains("Код для восстановления пароля"))
                    flag = false;
        }
        return lastMessage.split(" ")[4].replace(".","");

    }
}
