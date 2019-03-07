package utils;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import pages.BasePage;

import java.lang.reflect.Method;


public class BaseUITest {

    public ReporterManager reporter;

    @BeforeMethod
    public void beforeWithData(Object[] data, Method method) {

        //init reporter
        reporter = ReporterManager.Instance;
        reporter.startReporting(method, data);

        //init threadlocal driver
        try {
            reporter.info("Driver creation");
            BasePage.driver.set(DriverProvider.getDriver());
            if(DriverProvider.isProxy()) {
                String harFile = BrowserProxy.getInstance().createHARFileFromProxy(reporter.getTestName(method, data));
                reporter.info("HAR file creation: " + harFile);
            }
            //reporter.info("Driver created " + BasePage.driver.get().hashCode());
        }catch (Exception e){
            reporter.failWithScreenshot("Before test failure during Driver creation", e);
            reporter.stopReporting();
            reporter.closeReporter();
            Assert.fail();
        }

        //BasePage.driver().manage().window().maximize();

    }

    @AfterMethod
    public void endTest(ITestResult testResult){

        if(DriverProvider.isProxy()) {
            BrowserProxy.getInstance().dumpHARFileFromProxy();
            reporter.info("Har file saved");
        }

        // close reporter
        reporter.stopReporting(testResult);

        //close driver
        BasePage.driver().quit();
        DriverProvider.closeDriver();

    }

    @AfterSuite(alwaysRun = true)
    public void flushReporter() {
        reporter.closeReporter();
    }
}
