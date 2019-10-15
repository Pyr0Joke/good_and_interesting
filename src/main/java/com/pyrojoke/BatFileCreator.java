package com.pyrojoke;

import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class BatFileCreator {

    static void createBatFile(Har har){
        try {
            FileWriter writer = new FileWriter("src/main/resources/bug_curl.bat", false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("@echo off");
            bufferedWriter.newLine();
            bufferedWriter.write("SetLocal EnableDelayedExpansion");
            bufferedWriter.newLine();
            bufferedWriter.write("set order=1");
            bufferedWriter.newLine();
            String cookieFile = "";
            for(HarEntry entry: ReportIssue.clearHar(har).getLog().getEntries()){
                if(entry.getRequest().getUrl().contains("/api/v1/auth/login")|entry.getRequest().getUrl().contains("/api/v1/auth/loginByRestoreCode")){
                    cookieFile = " -b cookie.txt  --header \"X-XSRF-TOKEN: %token%\" ";
                    bufferedWriter.newLine();
                    bufferedWriter.write("set c=0");
                    bufferedWriter.newLine();
                    if(entry.getRequest().getUrl().contains("/api/v1/auth/loginByRestoreCode")){
                        bufferedWriter.write("curl -c cookie.txt -X " + entry.getRequest().getMethod() + " " + "\"" + entry.getRequest().getUrl() + "\"" + " -H \"accept: application/json\" -H \"Content-Type: application/json\" -d " + "\"" + entry.getRequest()
                                .getPostData()
                                .getText()
                                .replace(entry.getRequest().getPostData().getText().substring(entry.getRequest().getPostData().getText().indexOf("code"),entry.getRequest().getPostData().getText().indexOf("code")+16),"code\":\"%smscode%")
                                .replace("\\n", "")
                                .replace("\"", "\\\"").replace(" ", "").replaceAll("\\s+", "") + "\"");
                    }
                    else {
                        bufferedWriter.write("curl -c cookie.txt -X " + entry.getRequest().getMethod() + " " + "\"" + entry.getRequest().getUrl() + "\"" + " -H \"accept: application/json\" -H \"Content-Type: application/json\" -d " + "\"" + entry.getRequest()
                                .getPostData()
                                .getText()
                                .replace("\\n", "")
                                .replace("\"", "\\\"").replace(" ", "").replaceAll("\\s+", "") + "\"");
                    }
                    bufferedWriter.newLine();
                    bufferedWriter.write("for /f \"UseBackQ Delims=\" %%A IN (\"cookie.txt\") do (");
                    bufferedWriter.newLine();
                    bufferedWriter.write("  set /a c+=1");
                    bufferedWriter.newLine();
                    bufferedWriter.write(" set \"m!c!=%%A\"");
                    bufferedWriter.newLine();
                    bufferedWriter.write(")");
                    bufferedWriter.newLine();
                    bufferedWriter.write("SET token=%m4:devfe-srv01\tFALSE\t/\tFALSE\t0\tXSRF-TOKEN\t=%");
                    bufferedWriter.newLine();
                    bufferedWriter.write("echo %token%");
                    bufferedWriter.newLine();
                }
                else if(entry.getRequest().getUrl().contains("/api/v1/order/get?")){
                    bufferedWriter.write("curl "+cookieFile+" -X " + entry.getRequest().getMethod() + " " + "\"" + entry.getRequest().getUrl().replace("%3A", ":").replace("%2C", ",") + "\"" + " -H \"accept: application/json\" -O");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set c=0");
                    bufferedWriter.newLine();
                    bufferedWriter.write("for /f \"UseBackQ Delims=\" %%B IN (\"get_counterpartyId=588&deliveryMethodTypes=FREE_OF_CHARGE,TO_TEK,BY_BUS,PICKUP\") do (");
                    bufferedWriter.newLine();
                    bufferedWriter.write("  set /a c+=1");
                    bufferedWriter.newLine();
                    bufferedWriter.write("  set \"n!c!=%%B\"");
                    bufferedWriter.newLine();
                    bufferedWriter.write(")");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set order=%n1:~90,7%");
                    bufferedWriter.newLine();
                    bufferedWriter.write("echo %order%");
                    bufferedWriter.newLine();
                }
                else if (entry.getRequest().getUrl().contains("/api/v1/order/getOne?")){
                    bufferedWriter.write("if %order% gtr 1 ( ");
                    bufferedWriter.write("curl " + cookieFile + " -X " + entry.getRequest().getMethod() + " " + "\"" + entry.getRequest().getUrl().replace("%3A", ":").replace("%2C", ",").replace(entry.getRequest().getUrl().substring(entry.getRequest().getUrl().indexOf("id="),entry.getRequest().getUrl().indexOf("id=")+10), "id=%order%") + "\"" + " -H \"accept: application/json\" )");
                    bufferedWriter.write("else (");
                    bufferedWriter.write("curl " + cookieFile + " -X " + entry.getRequest().getMethod() + " " + "\"" + entry.getRequest().getUrl().replace("%3A", ":").replace("%2C", ",") + "\"" + " -H \"accept: application/json\" )");
                    bufferedWriter.newLine();
                }
                else if (entry.getRequest().getUrl().contains("/api/v1/order/getGoods?")|entry.getRequest().getUrl().contains("/api/v1/order/repeatInfoForOrder?")){
                    bufferedWriter.write("if %order% gtr 1 ( ");
                    bufferedWriter.write("curl " + cookieFile + " -X " + entry.getRequest().getMethod() + " " + "\"" + entry.getRequest().getUrl().replace("%3A", ":").replace("%2C", ",").replace(entry.getRequest().getUrl().substring(entry.getRequest().getUrl().indexOf("orderId="),entry.getRequest().getUrl().indexOf("orderId=")+15), "orderId=%order%") + "\"" + " -H \"accept: application/json\" )");
                    bufferedWriter.write("else (");
                    bufferedWriter.write("curl " + cookieFile + " -X " + entry.getRequest().getMethod() + " " + "\"" + entry.getRequest().getUrl().replace("%3A", ":").replace("%2C", ",") + "\"" + " -H \"accept: application/json\" )");
                    bufferedWriter.newLine();
                }
                else if(entry.getRequest().getUrl().contains("/api/v1/auth/restorePassword")){
                    bufferedWriter.write("curl " + cookieFile + " -X " + entry.getRequest().getMethod() + " " + "\"" + entry.getRequest().getUrl() + "\"" + " -H \"accept: application/json\" -H \"Content-Type: application/json\" -d " + "\"" + entry.getRequest()
                            .getPostData()
                            .getText()
                            .replace("\\n", "")
                            .replace("\"", "\\\"").replace(" ", "").replaceAll("\\s+", "") + "\"");
                    bufferedWriter.newLine();
                    bufferedWriter.write("curl -c admincookie.txt -X POST \"http://devfe-srv01:81/api/v1/auth/login\" -H \"accept: application/json\" -H \"Content-Type: application/json\" -d \"{\\\"login\\\":\\\"admin\\\",\\\"password\\\":\\\"a3ta8RtKj5\\\"}\"\n");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set c=0");
                    bufferedWriter.newLine();
                    bufferedWriter.write("for /f \"UseBackQ Delims=\" %%A IN (\"admincookie.txt\") do (");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set /a c+=1");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set \"m!c!=%%A\"");
                    bufferedWriter.newLine();
                    bufferedWriter.write(")");
                    bufferedWriter.newLine();
                    bufferedWriter.write("SET token=%m4:devfe-srv01\tFALSE\t/\tFALSE\t0\tXSRF-TOKEN\t=%");
                    bufferedWriter.newLine();
                    bufferedWriter.write("echo %token%");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set smscode=0");
                    bufferedWriter.newLine();
                    bufferedWriter.write("goto :logic");
                    bufferedWriter.newLine();
                    bufferedWriter.write(":logic");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set /p DUMMY=Hit ENTER to continue ...");
                    bufferedWriter.newLine();
                    bufferedWriter.write("if %smscode% gtr 0 (");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set /p DUMMY=Hit ENTER to continue ...");
                    bufferedWriter.newLine();
                    bufferedWriter.write("goto :end");
                    bufferedWriter.newLine();
                    bufferedWriter.write(") else (");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set /p DUMMY=Hit ENTER to continue ...");
                    bufferedWriter.newLine();
                    bufferedWriter.write("goto :label");
                    bufferedWriter.newLine();
                    bufferedWriter.write(")");
                    bufferedWriter.newLine();
                    bufferedWriter.write(":label");
                    bufferedWriter.newLine();
                    bufferedWriter.write("curl -b admincookie.txt --header \"X-XSRF-TOKEN: %token%\" -X GET \"http://devfe-srv01:81/api/v1/notification/getSmsHistory?page=0&size=25&address=+79969390287&sort=createdAt,desc\" -H \"accept: application/json\" -O ");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set c=0");
                    bufferedWriter.newLine();
                    bufferedWriter.write("for /f \"UseBackQ Delims=\" %%B IN (\"getSmsHistory_page=0&size=25&address=+79969390287&sort=createdAt,desc\") do (");
                    bufferedWriter.newLine();
                    bufferedWriter.write(" set /a c+=1");
                    bufferedWriter.newLine();
                    bufferedWriter.write(" set \"n!c!=%%B\"");
                    bufferedWriter.newLine();
                    bufferedWriter.write(")");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set smscode=%n1:~124,8%");
                    bufferedWriter.newLine();
                    bufferedWriter.write("echo %smscode%");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set /p DUMMY=Hit ENTER to continue ...");
                    bufferedWriter.newLine();
                    bufferedWriter.write("goto :logic");
                    bufferedWriter.newLine();
                    bufferedWriter.write(":end");
                    bufferedWriter.newLine();
                    bufferedWriter.write("set /p DUMMY=Hit ENTER to continue ...");
                    bufferedWriter.newLine();
                    bufferedWriter.write("");
                    bufferedWriter.newLine();
                }
                else{
                    if (entry.getRequest().getMethod().equalsIgnoreCase("get")) {
                        if(entry.getRequest().getUrl().contains("orderId=")){
                            bufferedWriter.write("curl " + cookieFile + " -X " + entry.getRequest().getMethod() + " " + "\"" + entry.getRequest().getUrl().replace("%3A", ":").replace("%2C", ",").replace(entry.getRequest().getUrl().substring(entry.getRequest().getUrl().indexOf("orderId="),entry.getRequest().getUrl().indexOf("orderId=")+15), "orderId=%order%").replace("%D0%93%D0%9A","ГК") + "\"" + " -H \"accept: application/json\"");
                            bufferedWriter.newLine();
                        }
                        else {
                            bufferedWriter.write("curl " + cookieFile + " -X " + entry.getRequest().getMethod() + " " + "\"" + entry.getRequest().getUrl().replace("%3A", ":").replace("%2C", ",").replace("%D0%93%D0%9A","ГК") + "\"" + " -H \"accept: application/json\"");
                            bufferedWriter.newLine();
                        }
                    }
                    if (entry.getRequest().getMethod().equalsIgnoreCase("post")) {
                        if (entry.getRequest().getPostData().getText().contains("orderId")) {
                            bufferedWriter.write("curl " + cookieFile + " -X " + entry.getRequest().getMethod() + " " + "\"" + entry.getRequest().getUrl() + "\"" + " -H \"accept: application/json\" -H \"Content-Type: application/json\" -d " + "\"" + entry.getRequest()
                                    .getPostData()
                                    .getText()
                                    .replace(entry.getRequest().getPostData().getText().substring(entry.getRequest().getPostData().getText().indexOf("orderId"), entry.getRequest().getPostData().getText().indexOf("orderId") + 17), "orderId\": %order%")
                                    .replace("\\n", "")
                                    .replace("\"", "\\\"").replace(" ", "").replaceAll("\\s+", "") + "\"");
                            bufferedWriter.newLine();
                        }
                        else {
                            bufferedWriter.write("curl " + cookieFile + " -X " + entry.getRequest().getMethod() + " " + "\"" + entry.getRequest().getUrl() + "\"" + " -H \"accept: application/json\" -H \"Content-Type: application/json\" -d " + "\"" + entry.getRequest()
                                    .getPostData()
                                    .getText()
                                    .replace("\\n", "")
                                    .replace("\"", "\\\"").replace(" ", "").replaceAll("\\s+", "") + "\"");
                            bufferedWriter.newLine();
                        }
                    }
                }
                bufferedWriter.write("set /p DUMMY=Hit ENTER to continue ...");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
