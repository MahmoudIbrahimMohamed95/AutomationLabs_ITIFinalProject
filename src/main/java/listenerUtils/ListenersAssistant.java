package listenerUtils;

import fileUtils.FileAndTerminalManager;
import readers.Log;
import readers.PropertyReader;

import java.io.File;

public class ListenersAssistant {

    public static ListenersAssistant cleanTestOutputDirectories() {

        FileAndTerminalManager.cleanDirectory(new File(System.getProperty("user.dir")+
                "/test-output/allure-results"+File.separator));
        Log.info("allure-results directory cleaned");

        FileAndTerminalManager.cleanDirectory(new File(System.getProperty("user.dir")+
                File.separator+ PropertyReader.getProperty("ScreenShot_Path")));
        Log.info("ScreenShots directory cleaned");

        FileAndTerminalManager.cleanDirectory(new File(PropertyReader.getProperty("user.dir")+File.separator
                +PropertyReader.getProperty("Recording_Path")));
        Log.info("Recordings directory cleaned");

        FileAndTerminalManager.forceDelete(new File(PropertyReader.getProperty("user.dir")+
                File.separator+"test-output/Logs"+File.separator+"logs.log"));
        Log.info("Logs directory cleaned");
        return new ListenersAssistant();
    }

    public static ListenersAssistant createTestOutputDirectories() {
        FileAndTerminalManager.createDirectory(new File(System.getProperty("user.dir")+
                                                            File.separator+
                                                            PropertyReader.getProperty("ScreenShot_Path")));
        Log.info("ScreenShots directory Created");

        FileAndTerminalManager.createDirectory(new File(System.getProperty("user.dir")+
                File.separator+PropertyReader.getProperty("Recording_Path")));
        Log.info("Recordings directory Created");
        return new ListenersAssistant();
    }

}
