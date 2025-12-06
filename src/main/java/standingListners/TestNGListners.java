package standingListners;

import assertions.Assertions;
import coreMedia.ScreenRecordManager;
import coreMedia.ScreenshotsManager;
import driverFactory.WebDriverFactory;
import listenerUtils.ListenersAssistant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverInfo;
import org.testng.*;
import readers.Log;
import readers.PropertyReader;
import reports.AllureAttachmentManager;
import reports.AllureGenerator;
import reports.AllureSetupEnvironment;

import java.lang.reflect.Field;

import static reports.AllureAttachmentManager.attachLogs;

public class TestNGListners implements ISuiteListener, IExecutionListener, IInvokedMethodListener, ITestListener {
//	WebDriver driver;
//	driver= WebDriverFactory.get();


	@Override
	public void onExecutionStart() {

		Log.info("Test Execution started");
		PropertyReader.loadProperties();

		ListenersAssistant.cleanTestOutputDirectories()
				.createTestOutputDirectories();

		AllureSetupEnvironment.setAllureEnvironment()
				.downloadAndExtract();

	}

	@Override
	public void onExecutionFinish() {
		AllureGenerator.copyHistory()
				.generateSingleReport()
				.generateFullReport()
				.openReport();
		Log.info("Test Execution Finished");

	}


	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// not implemented
		if (method.isTestMethod()) {

			ScreenRecordManager.startRecording();
		}
	}

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// not implemented
		WebDriver driver=null;
		if (method.isTestMethod()) {
			// safe to attach

			try {
				driver = WebDriverFactory.get();
				ScreenshotsManager.captureScreenshot(driver, testResult.getName());
				ScreenRecordManager.stopRecording(testResult.getName());
				AllureAttachmentManager.attachRecords(testResult.getName());
				attachLogs();

				// Now you can use driver (e.g., take screenshot)
			} catch (Exception e) {
				Log.error("Error while capturing screenshot: " + e.getMessage());
			}
		}

		Assertions.assertAll(testResult);
	}
}
