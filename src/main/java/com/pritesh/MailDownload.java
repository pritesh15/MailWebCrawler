package com.pritesh;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * Created by pripatha on 10/31/2017.
 */
public class MailDownload{
    public void getMails(String year, WebDriver driver){
        String url = driver.getCurrentUrl();
        //Creating 12 threads for each month to fetch the messages.
        for(int i=1;i<=12;i++){
            String month = ((Integer)i).toString();
            if(i<10){
                month = "0"+month;
            }
            try{
                driver.findElement(By.id(year+month));
            }catch (NoSuchElementException ex){
                break;
            }
            String path = url+year+month+".mbox/browser";
            MailFetch mailFetch = new MailFetch(year+month,path);
            Thread mailFetchThread = new Thread(mailFetch);
            mailFetchThread.start();
        }
    }

}
