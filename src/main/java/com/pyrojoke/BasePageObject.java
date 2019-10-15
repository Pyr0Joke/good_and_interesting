package com.pyrojoke;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;


public class BasePageObject {


    private static final By ANIMATION_LOCATOR = By.xpath("//div[@class='ui-modal ng-trigger ng-trigger-modalState ng-animating']");
    protected static final String COLOR_BUTTON = "(255, 255, 255";
    private static final By ITEM_LIST = By.xpath("//div/li");
    protected WebDriver driver;
    protected Logger log;


    public BasePageObject(WebDriver driver, Logger log, By element) {
        this.driver = driver;
        this.log = log;
        waitForVisibilityOf(element);
    }


    protected WebElement find(By locator){
        waitForVisibilityOf(locator, 30);
        return driver.findElement(locator);
    }

    protected void click(By locator){
        waitForVisibilityOf(locator, 30);
        find(locator).click();
    }

    public void waitForVisibilityOf(By locator, Integer... timeOutInSeconds) {
        int attempts = 0;
        while(attempts < 2){
            try {
                waitForWebElementCondition(ExpectedConditions.visibilityOfElementLocated(locator), (timeOutInSeconds.length> 0 ? timeOutInSeconds[0] : null));
                break;
            }
            catch (StaleElementReferenceException e){
                e.printStackTrace();
            }
            attempts++;
        }
    }

    public void waitForInvisibilityOf(By locator, Integer... timeOutInSeconds) {
        int attempts = 0;
        while(attempts < 2){
            try {
                waitForBooleanCondition(ExpectedConditions.attributeContains(locator, "style", "hidden"), (timeOutInSeconds.length> 0 ? timeOutInSeconds[0] : null));
                break;
            }
            catch (StaleElementReferenceException e){
                e.printStackTrace();
            }
            attempts++;
        }
    }

    public void waitForClickableOf(By locator, Integer... timeOutInSeconds) {
        int attempts = 0;
        while(attempts < 2){
            try {
                waitForWebElementCondition(ExpectedConditions.elementToBeClickable(locator), (timeOutInSeconds.length> 0 ? timeOutInSeconds[0] : null));
                break;
            }
            catch (StaleElementReferenceException e){
                e.printStackTrace();
            }
            attempts++;
        }
    }

    private void waitForBooleanCondition(ExpectedCondition<Boolean> condition, Integer timeOutInSeconds){
        timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds:30;
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(condition);
    }

    private void waitForWebElementCondition(ExpectedCondition<WebElement> condition, Integer timeOutInSeconds){
        timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds:30;
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(condition);
    }

    protected List<WebElement> findAll(By locator){
        waitForVisibilityOf(locator, 5);
        return driver.findElements(locator);
    }

    protected void typeRequestOnSearchFieldAndSearch(String request){
        By searchFiledLocator = By.xpath("//input[@name='search']");
        find(searchFiledLocator).sendKeys(request);
        find(searchFiledLocator).sendKeys(Keys.ENTER);
    }

    protected Double takePrice(By locator){
        return Double.parseDouble(find(locator).getText().replace(" ","").replace("\u20BD","").replace("/ед","").replace(",","."));
    }

    protected void addDocuments(By locator){
//        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        File file = new File("src/main/resources/import_template.xlsx");

        find(locator).click();
        try {
//            StringSelection ss = new StringSelection(screenshot.getAbsolutePath());
            StringSelection ss = new StringSelection(file.getAbsolutePath());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
            Robot robot = new Robot();
            robot.delay(500);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(500);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(500);
        }
        catch (AWTException e){
            e.printStackTrace();
        }
    }

    public void selectItemFromDropDownList(By locator, String item){
        find(locator).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for(WebElement listElement: findAll(ITEM_LIST)){
            if(listElement.getText().equals(item)) {
                listElement.click();
                break;
            }
            else{
                js.executeScript("arguments[0].scrollIntoView();", listElement);
            }
        }
    }


    public void selectItemFromDropDownList(WebElement locator, String item){
        locator.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for(WebElement listElement: findAll(ITEM_LIST)){
            if(listElement.getText().equals(item)) {
                listElement.click();
                break;
            }
            else{
                js.executeScript("arguments[0].scrollIntoView();", listElement);
            }
        }
    }

    public WebElement findByValue(String cssSelector, String value){
        return find(By.xpath("//"+cssSelector+"[@value='"+value+"']"));
    }

    public void waitAnimation(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.invisibilityOf(find(ANIMATION_LOCATOR)));
    }
}


