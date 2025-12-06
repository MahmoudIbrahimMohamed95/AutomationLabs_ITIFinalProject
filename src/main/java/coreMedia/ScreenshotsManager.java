package coreMedia;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import readers.Log;
import readers.PropertyReader;
import reports.AllureAttachmentManager;

import java.io.File;

public class ScreenshotsManager {
   public static void captureScreenshot(WebDriver driver, String shotName) {
        try {
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenshotDest = new File(System.getProperty("user.dir")+File.separator+
                                    PropertyReader.getProperty("ScreenShot_Path") + shotName +".png");
            FileUtils.copyFile(screenshotSrc,screenshotDest);
            AllureAttachmentManager.attachScreenshot(shotName,screenshotDest.toPath());
            Log.info("Successed To Capture Screenshot ");
        } catch (Exception e) {
            Log.error("Failed to Capture Screenshot " + e.getMessage());
        }
    }
}
