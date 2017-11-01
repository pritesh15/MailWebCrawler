package com.pritesh;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by pripatha on 10/31/2017.
 */
public class StoreMessage implements Runnable {
    private String href;
    private String fileName;
    private File dir;
    private WebDriver driver;
    public StoreMessage(String href,File dir,String fileName,WebDriver driver){
        this.href=href;
        this.dir=dir;
        this.fileName=fileName;
        this.driver = driver;
    }
    public void run(){
        // msgview[][] is a 2-d array to store the values from message table such as, From, Subject, Date, and Contents
        String msgView[][] = new String[5][3];
        synchronized (driver) {
            driver.get(href);

            for (int i = 1; i <= 4; i++) {

                for (int j = 1; j <= 2 && i != 4; j++) {
                    msgView[i][j] = (driver.findElement(By.xpath(".//*[@id='msgview']/tbody/tr[" + i + "]/td[" + j + "]")).getText());
                }
                if (i == 4) {
                    msgView[i][1] = "contents";
                    msgView[i][2] = driver.findElement(By.xpath(".//*[@id='msgview']/tbody/tr[4]/td/pre")).getText();
                }
            }
        }
        storeMessageInFile(dir,fileName,msgView);
    }

    public void storeMessageInFile(File dirName,String fileName, String[][] msgView){
        File file = new File(dirName,fileName);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=1;i<=4;i++){
            for(int j=1;j<=2;j++){
                writer.print(msgView[i][j] + " ");
            }
            writer.println();
        }
        writer.flush();
        writer.close();

        //System.out.println(fileName.toString()+" created successfully");
    }
}
