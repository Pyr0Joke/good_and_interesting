package com.pyrojoke.utilities;

import com.pyrojoke.BaseTest;
import io.qameta.allure.Attachment;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.util.ArrayList;
import java.util.List;

public class StepListener implements StepLifecycleListener {


    //TODO дописать методы по очистке списка тут, а не в других местах
    public static List<String> steps = new ArrayList<>();
    @Override
    public void beforeStepStart(StepResult result) {
        steps.add(result.getName());
    }

    @Override
    public void beforeStepStop(StepResult result) {
        byte[] srcFile =  ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.BYTES);
        saveScreenshot(srcFile);
    }



    @Attachment(value = "Скриншот шага", type = "image/png")
    public byte[] saveScreenshot(byte[] screenshot){
        return screenshot;
    }
}
