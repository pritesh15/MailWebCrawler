package com.pritesh;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by pripatha on 10/31/2017.
 */
public class Main {

    public static void main(String[] args) throws Exception{
        String url = "http://mail-archives.apache.org/mod_mbox/maven-users/";
        String year = "2016";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the year for which you need to fetch the mails: ");
        year = sc.nextLine();
        Driver driverClass  = new Driver();
        System.out.println("All your messages can be obtained at location:"+ driverClass.getPath()+year);
        Thread.sleep(5000);
        WebDriver driver = driverClass.getDriver();
        driver.get(url);
        MailDownload mailDownload = new MailDownload();
        mailDownload.getMails(year, driver);

    }

}
