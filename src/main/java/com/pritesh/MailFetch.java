package com.pritesh;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pripatha on 10/31/2017.
 */
public class MailFetch implements Runnable{
    private String dirName;
    String url;


    public MailFetch(String dirName, String path){
        this.dirName = dirName;
        this.url=path;
    }
    public void run(){
        Driver driverClass  = new Driver();
        WebDriver driver = driverClass.getDriver();
        driver.get(url);

        //Waiting for ajax load for 200 sec
        WebDriverWait wait = new WebDriverWait(driver,300);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("msglist")));
        }catch(Exception e){
            System.out.println("Timeout occurred. 300 seconds exceeded to load ajax");
         }
//
//            try {
//                Thread.sleep(50000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        //Collecting all the 'a' tags in a list
        List<WebElement> list = driver.findElements(By.tagName("a"));

        //Create dir path as <proj-root-path>/<year>/<yearMonth>
        File dir = new File(driverClass.getPath()+dirName.substring(0,4)+"\\"+dirName);
        dir.mkdirs();
        int msgCount = 1;
        ExecutorService executor = Executors.newFixedThreadPool(5);

        //Get href attribute from each element in the list and check if they are message links "http://../<msg-id>"
        //Then, store the messages in a file by calling storeMessage
        for(WebElement element: list) {
            try{
                String href = element.getAttribute("href");
                if(!(href.contains("%3c") && href.contains("%3e")))
                    continue;
                //System.out.println("Fetch mail For "+ url + " " + Thread.currentThread().getId()  + href);
                String fileName = "msg-"+((Integer)msgCount).toString()+".txt";
                StoreMessage storeMessage = new StoreMessage(href,dir,fileName,driver);
                executor.execute(storeMessage);
            }catch (Exception ex){

            }

            msgCount++;
        }
        executor.shutdown();

    }
}
