package com.pritesh;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by pripatha on 31/10/17.
 */
public class Driver {
    private static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";
    private WebDriver driver ;
    private DesiredCapabilities desiredCaps ;
    private static String path;
    public String getPath(){
        return path;
    }
    public Driver(){
        //path = "C:\\Users\\pripatha.ORADEV\\IdeaProjects\\MailWebCrawler\\";

        path = Driver.class.getProtectionDomain().getCodeSource().getLocation().getPath();//.toURI().getPath();
        path = path.replace("MailWebCrawler.jar","");
//            System.out.println(path);

        desiredCaps = new DesiredCapabilities();
        desiredCaps.setJavascriptEnabled(true);
        desiredCaps.setCapability("takesScreenshot", false);
        desiredCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, path+"phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
        desiredCaps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "User-Agent", USER_AGENT);
        ArrayList<String> cliArgsCap = new ArrayList();
        cliArgsCap.add("--web-security=false");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=true");
        cliArgsCap.add("--webdriver-loglevel=ERROR");

        desiredCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        try{
            driver = new PhantomJSDriver(desiredCaps);
        }catch(Exception e){
            System.out.printf("phantomjs.exe not present at location: %s Download zip file for windows from " +
                    "http://phantomjs.org/download.html and unzip it here%n", PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY);
        }
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    public WebDriver getDriver(){
        return driver;
    }

    public void removeDriver(){
        driver.close();
    }
}
