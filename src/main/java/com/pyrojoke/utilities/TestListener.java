package com.pyrojoke.utilities;

import com.pyrojoke.BaseTest;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;

public class TestListener extends TestListenerAdapter{

    @Override
    public void onTestFailure(ITestResult tr) {
        Object currentClass = tr.getInstance();
        WebDriver driver = ((BaseTest) currentClass).getDriver();
        byte[] srcFile =  ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        File image =  ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        saveScreenshot(srcFile);
//        ReportIssue.createIssue(tr, image);
    }

    @Attachment(value = "Скриншот", type = "image/png")
    public byte[] saveScreenshot(byte[] screenshot){
        return screenshot;
    }
}
