package utils;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;


public class BaseTest {

    public ReporterManager reporter;

    @BeforeMethod
    public void beforeWithData(Object[] data, Method method) {
        //init reporter
        reporter = ReporterManager.Instance;
        reporter.startReporting(method, data);
    }

    @AfterMethod
    public void endTest(ITestResult testResult){
        // close reporter
        reporter.stopReporting(testResult);
    }

    @AfterSuite(alwaysRun = true)
    public void flushReporter() {
        reporter.closeReporter();
    }
}
