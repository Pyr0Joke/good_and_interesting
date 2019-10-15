package com.pyrojoke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WorkWith1C {

    //TODO переписать проверку законченной работы 1С не через tasklist, а через PID файл
    public static void exec1C(String batFineName) throws IOException {
        Runtime.getRuntime().exec("cmd /c start d:/bats/"+batFineName+".bat ");
        boolean flag = true;
        while(flag) {
            String[] cmd = {"tasklist", "/fi", "\"IMAGENAME eq 1cv8c.exe\""};
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("1cv8c.exe")) {
                    System.out.println("Один эс зопущен в первый раз");
                    flag=false;
                    break;
                }
            }
            br.close();
            isr.close();
            is.close();
        }

        flag = true;

        while(flag) {
            String[] cmd = {"tasklist", "/fi", "\"IMAGENAME eq 1cv8c.exe\""};
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            boolean is1CStarted = false;
            List<String> consoleOutputList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                consoleOutputList.add(line);
            }
            for (String consoleLine: consoleOutputList){
                if(consoleLine.contains("1cv8c.exe")){
                    System.out.println("Один эс зопущен и чото делоет");
                    is1CStarted=true;
                }
            }
            flag=is1CStarted;
            br.close();
            isr.close();
            is.close();
        }
        System.out.println("Один эс выключился");
    }
}
