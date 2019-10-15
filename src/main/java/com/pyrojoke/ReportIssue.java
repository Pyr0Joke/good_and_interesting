package com.pyrojoke;

import com.pyrojoke.utilities.StepListener;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import org.testng.ITestResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;


public class ReportIssue extends StepListener {

    private static final String BASE_URL = "http://jira.{company_name}.com/rest/api/2";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";

    public static void createIssue(ITestResult tr, File image){

        String priority = getPriority(tr.getMethod().getPriority());
        String browser = tr.getTestContext().getCurrentXmlTest().getAllParameters().get("browser");


        StringBuilder description = new StringBuilder();
        description.append("Шаги воспроизведения: " + "\\n");
        for (int i = 0; i < steps.size(); i++) {
            int n = i + 1;
            description.append(n + ". " + steps.get(i) + "\\n");
        }

        if(tr.getThrowable().getMessage().contains("Build info")){
            if (tr.getThrowable().getMessage()!=null) {
                description.append("Причина падения теста: " +tr.getThrowable().getMessage().substring(0,tr.getThrowable().getMessage().indexOf("Build info")-2).replace("\"","'") + "\\n");
            }
        }
        else{
            if (tr.getThrowable().getMessage()!=null) {
                description.append("Причина падения теста: " + tr.getThrowable().getMessage().replace("\"","") + "\\n");
            }
        }
        description.append("Browser: " + browser);


        RestAssured.baseURI = BASE_URL;
        byte[] message = (USERNAME + ":" + PASSWORD).getBytes(StandardCharsets.UTF_8);
        String auth = Base64.getEncoder().encodeToString(message);
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", "Basic " + auth);
        String data =
                "{\"fields\": {\"project\": { \"key\": \"{project_name}\" }, \"summary\": \" Проблема сценария: " + tr.getMethod().getDescription() + "\", \"description\": \"" + description + "\", \"issuetype\": { \"name\": \"Bug\" } } }";

        String priorityData = "{\"update\":{\"priority\":[{\"set\":{\"name\" : \"" + priority + "\"}}]}}";

        File logs = null;
        try{
            logs = File.createTempFile("logs",".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(logs));

            Har har = BrowserDriverFactory.proxy.getHar();

//            BatFileCreator.createBatFile(har);
            for (HarEntry  entry: clearHar(har).getLog().getEntries()){
                    writer.write("Request   ");
                    writer.write(entry.getRequest().getMethod()+"  ");
                    writer.write(entry.getRequest().getUrl());
                    writer.write(System.lineSeparator());
                    if(entry.getRequest().getPostData()!=null){
                        writer.write(entry.getRequest().getPostData().getText());}
                    writer.write(System.lineSeparator());
                    writer.write(System.lineSeparator());
                    writer.write("Response  ");
                    writer.write(entry.getResponse().getStatus()+" ");
                    writer.write(entry.getResponse().getStatusText());
                    writer.write(System.lineSeparator());
                    if(entry.getResponse().getContent()!=null&&entry.getResponse().getContent().getText()!=null){
                        writer.write(entry.getResponse().getContent().getText());
                    }
                    writer.write(System.lineSeparator());
                    writer.write(System.lineSeparator());
                }
            writer.flush();
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        File batFile = new File("src/main/resources/bug_curl.bat");
        Response response = given().headers(headerMap).body(data).post("/issue");
        String issueNumber = response.jsonPath().get("key");

        given().header("X-Atlassian-Token", "nocheck").header("Authorization", "Basic " + auth).and().multiPart(image).post(BASE_URL + "/issue/" + issueNumber + "/attachments");
        given().header("X-Atlassian-Token", "nocheck").header("Authorization", "Basic " + auth).and().multiPart(logs).post(BASE_URL + "/issue/" + issueNumber + "/attachments");
        given().header("X-Atlassian-Token", "nocheck").header("Authorization", "Basic " + auth).and().multiPart(batFile).post(BASE_URL + "/issue/" + issueNumber + "/attachments");

        given().header("X-Atlassian-Token", "nocheck").headers(headerMap).and().body(priorityData).put(BASE_URL + "/issue/" + issueNumber);
        given().headers(headerMap).header("X-Atlassian-Token", "nocheck").and().body("{\"name\":\"{Executor_name}\"}").put(BASE_URL + "/issue/"+issueNumber+"/assignee");

        Allure.issue("http://jira.{company_name}.com/browse/"+issueNumber, "http://jira.{company_name}.com/browse/"+issueNumber);
        BrowserDriverFactory.proxy.getHar().getLog().getEntries().clear();
    }

    private static String getPriority(int index) {
        String priority;
        switch (index) {
            case 1:
                priority = "Blocker";
                break;
            case 2:
                priority =  "High";
                break;
            case 3:
                priority = "Medium";
                break;
            case 4:
                priority = "Low";
                break;
            case 5:
                priority = "Lowest";
                break;
            default:
                priority = "Medium";
                break;
        }
        return priority;
    }

    public static Har clearHar(Har har){
        har.getLog().getEntries().removeIf(n -> (
                n.getRequest().getUrl().contains(".js")||
                n.getRequest().getMethod().contains("CONNECT")||
                        n.getRequest().getUrl().contains(".png")||
                        n.getRequest().getUrl().contains(".css")||
                        n.getRequest().getUrl().contains(".svg")||
                        n.getRequest().getUrl().contains("tracking-protection")||
                        n.getRequest().getUrl().contains("shavar.services")||
                        n.getRequest().getUrl().contains(".woff2")||
                        n.getRequest().getUrl().contains("digicert")||
                        n.getRequest().getUrl().contains(".ico")||
                        n.getRequest().getUrl().contains(".jpg")||
                        n.getRequest().getUrl().contains("/ws/")||
                        n.getRequest().getUrl().contains(".xml")||
                        n.getRequest().getUrl().contains(".zip")||
                        n.getRequest().getUrl().contains("push.services")||
                        n.getRequest().getUrl().contains("snippets")||
                        n.getRequest().getUrl().contains("detectportal.firefox")||
                        n.getRequest().getUrl().contains("aus5.mozilla")||
                        n.getRequest().getUrl().contains("mc.yandex.ru")||
                        n.getRequest().getUrl().contains("mc.yandex.md")||
                        n.getRequest().getUrl().contains("doubleclick.net")||
                        n.getRequest().getUrl().contains(".gif")||
                        n.getRequest().getUrl().contains("google")||
                        n.getRequest().getUrl().contains("detectportal.")
        ));
        return har;
    }
}
