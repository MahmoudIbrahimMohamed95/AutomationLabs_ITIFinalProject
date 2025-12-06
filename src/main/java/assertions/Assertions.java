package assertions;

import businessPages.IQA_ProductPage;
import businessPages.QA_Cart;
import businessPages.QA_CheckOut;
import businessPages.QA_Confirmation;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;
import readers.Log;


public class Assertions {

    protected static SoftAssert softAssert = new SoftAssert();
    public static void assertAll(ITestResult result) {
        try {
            softAssert.assertAll();
        }
        catch (AssertionError e)
        {
            Log.error("Assertion failed:"+ e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        }
        finally {
            softAssert = new SoftAssert(); // Reset the soft assert instance
        }
    }

    public   Assertions assertFalse(boolean condition ,String message) {
        softAssert.assertFalse(condition, message);
        return this;
    }

    public   Assertions assertEqual(String actual ,String expected,String message) {
        softAssert.assertEquals(actual, expected,message);
        return this;
    }

    public  Assertions assertNotEqual(String actual ,String expected,String message) {
        softAssert.assertNotEquals(actual, expected,message);
        return this;
    }

    public  Assertions assertTrue(boolean condition ,String message) {
        softAssert.assertTrue(condition, message);
        return this;
    }


}
