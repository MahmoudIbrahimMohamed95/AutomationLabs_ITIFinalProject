package reports;

import fileUtils.FileAndTerminalManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import readers.Log;
import readers.PropertyReader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureAttachmentManager {
    public static void attachScreenshot(String screenName, Path screenPath) {
        try {
            if (Files.exists(screenPath)) {
                Allure.addAttachment(screenName, Files.newInputStream(screenPath));
                Log.info("Screenshot is Attached Successfully: " + screenPath.toString());
            } else {
                Log.error("Screenshot not found: " + screenPath.toString());
            }
        } catch (Exception e) {
            Log.error("Error in attaching screenshot", e.getMessage());
        }
    }
    public static void attachLogs() {
        try {
            LogManager.shutdown();
            File logFile = new File(System.getProperty("user.dir")
                                    +File.separator+"test-output/Logs"+File.separator+"logs.log");
            ((LoggerContext) LogManager.getContext(false)).reconfigure();
            if (logFile.exists()) {
                Allure.attachment("logs.log", Files.readString(logFile.toPath()));
                Log.info("Logs is Attached Successfully");
            }
        } catch (Exception e) {
                Log.error("Error attaching logs", e.getMessage());
        }
    }
    public static void attachRecords(String testMethodName) {
        if (PropertyReader.getProperty("screen-record").equalsIgnoreCase("true")) {
            try {
                File record = new File(PropertyReader.getProperty("user.dir")+
                                                File.separator+PropertyReader.getProperty("Recording_Path")+testMethodName+".mp4");

                if (record != null &&record.getName().startsWith(testMethodName) &&record.getName().endsWith(".mp4")) {
                    Allure.addAttachment(testMethodName, "video/mp4", Files.newInputStream(record.toPath()), ".mp4");
                    Log.info("records is attached successfully");
                }
            } catch (Exception e) {
                Log.error("Error attaching records", e.getMessage());
            }
        }
    }
}
