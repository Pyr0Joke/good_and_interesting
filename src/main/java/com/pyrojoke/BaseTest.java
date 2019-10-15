package com.pyrojoke;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class BaseTest{

    public static WebDriver driver;

    protected Logger log;

    public void setUp() { }

    public void tearDown() { }

    public WebDriver getDriver(){
        return driver;
    }
}
