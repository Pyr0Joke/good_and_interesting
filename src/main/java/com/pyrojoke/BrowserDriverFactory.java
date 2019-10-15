package com.pyrojoke;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import java.net.Inet4Address;
import java.net.UnknownHostException;


public class BrowserDriverFactory {
    public static BrowserMobProxy proxy  = new BrowserMobProxyServer();
    public static Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
    private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    private String browser;
    private Logger log;
    static {proxy.setTrustAllServers(true);}

    public BrowserDriverFactory(String browser, Logger log) {
        this.browser = browser.toLowerCase();
        this.log=log;
    }

    public WebDriver createDriver(){

        log.info("Create driver: " + browser);

//        proxy.setTrustAllServers(true);
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        if(!proxy.isStarted())
            proxy.start();

        seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        try {
            String hostIp = Inet4Address.getLocalHost().getHostAddress();
            seleniumProxy.setHttpProxy(hostIp + ":" + proxy.getPort());
            seleniumProxy.setSslProxy(hostIp + ":" + proxy.getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Assert.fail("invalid Host Address");
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.merge(capabilities);
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver.set(new ChromeDriver(chromeOptions));
                proxy.newHar();
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
//                FirefoxBinary firefoxBinary = new FirefoxBinary();
//                firefoxBinary.addCommandLineOptions("--headless");
//                firefoxOptions.setBinary(firefoxBinary);
                firefoxOptions.merge(capabilities);
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver.set(new FirefoxDriver(firefoxOptions));
                proxy.newHar();
                break;
            case "opera":
                OperaOptions operaOptions = new OperaOptions();
                operaOptions.merge(capabilities);
                System.setProperty("webdriver.opera.driver", "src/main/resources/operadriver.exe");
                driver.set(new OperaDriver(operaOptions));
                proxy.newHar();
                break;

            default:
                log.info("Do not know how to start: " + browser + ", starting firefox.");
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver.set(new ChromeDriver());
                break;
        }

        return driver.get();
    }
}
