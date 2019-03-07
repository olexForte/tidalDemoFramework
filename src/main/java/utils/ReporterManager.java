package utils;

/**
 * Reporter
 */

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporterManager {

    public static final String MARKER_OF_FAILED_ITEM = "----";
    private static ReporterManager instance;
    public static ReporterManager Instance = (instance != null) ? instance : new ReporterManager();

    ReporterManager(){
        System.out.println();
    }

    private static Map<Long, ExtentTest> testThread = new HashMap<Long, ExtentTest>();
    private static ExtentReports extent;

    private static final Logger logger = LoggerFactory.getLogger(ReporterManager.class);

    private synchronized static ExtentReports getInstance() {
        if (extent == null) {
            extent = new ExtentReports(FileIO.TARGET_FOLDER + File.separator + "Report" + Tools.getCurDateTime() + ".html", true, NetworkMode.ONLINE);
        }
        return extent;
    }


    public synchronized  Map<Long, ExtentTest> startTest(Method m, String testName, String testDescription) {
        Long threadID = Thread.currentThread().getId();

        ExtentTest test = getInstance().startTest(testName, testDescription);
        //test.assignCategory(DriverProvider.getCurrentBrowserName());
        for (String gr : getTestGroups(m)) {
            test.assignCategory(gr);
        }
        testThread.put(threadID, test);
        return testThread;
    }

    public synchronized static ExtentTest report() {
        ExtentTest report = null;
        Long threadID = Thread.currentThread().getId();
        if (testThread.containsKey(threadID)) {
            report = testThread.get(threadID);
        }
        return report;
    }

    public synchronized static void closeTest() {
        getInstance().endTest(report());
    }


    public synchronized static void closeReporter() {
        getInstance().flush();
    }

    public String getTestName(Method m, Object[] data) {
        String testName = null;
        String address = null;

        // if groups specified check that Group name () contains link to ticket
        String[] testGroups = m.getAnnotation(Test.class).groups();
        for (int i = 0; i < testGroups.length; i++) {
            if (testGroups[i].startsWith("http")) {
                address = testGroups[i];
            }
        }
        // if Group name contains web address - "hide" this address in Link <br>
        //testName = @Test testName()
        if (address != null) {
            testName = "<a href=" + "\"" + address + "\""
                    + "target=_blank alt=This test is linked to test case. Click to open it>"
                    + m.getAnnotation(Test.class).testName() + "</a>";
        } else {

                if(!m.getAnnotation(Test.class).testName().equals(""))
                    testName = m.getAnnotation(Test.class).testName();
                else
                    testName = m.getName();
                if(data != null && data.length != 0) testName = testName + " - " + Arrays.asList(data).stream().map(o -> (String)o).collect(Collectors.joining(", ", "[","]"));
        }

        // default behaviour - use method name as test name
        if (testName == null || testName.equals("")) {
            testName = m.getName();
        }
        return testName;
    }

    private String getTestDescription(Method m) {
        String testDescription = null;
        testDescription = m.getAnnotation(Test.class).description();
        if (testDescription == null || testDescription.equals("")) {
            testDescription = "";
        }
        return testDescription;
    }


    private String[] getTestGroups(Method m) {
        String[] testGroups = m.getAnnotation(Test.class).groups();
        if (testGroups == null || testGroups.equals("")) {
            testGroups[0] = "";
        }
        return testGroups;
    }


    public void startReporting(Method m, Object[] data) {
        startTest(m, getTestName(m, data), getTestDescription(m));
        String testGroups = "";
        for (String gr : getTestGroups(m)) {
            testGroups = testGroups + gr + "; ";
        }
        logger.info(
                "--------------------------------------------------------------------------------------------------------");
        logger.info("Started test '" + getTestName(m, data) + "' Groups: '" + testGroups.trim() + "'");
    }

    public void stopReporting(){
        closeTest();
    }

    public void stopReporting(ITestResult result) {
        closeTest();

        if (result.getStatus() == ITestResult.FAILURE)
            failWithScreenshot("Test failed", result.getThrowable());
        else if (result.getStatus() == ITestResult.SKIP)
            info("Test skipped");
        else
            pass("Test passed");
    }

    /**
     * show info step in report
     * @param details
     */
    public void info(String details) {
        logger.info(details);
        report().log(LogStatus.INFO, details.replace("\n", "<br>"));
    }

    /**
     * show pass step in report
     * @param details
     */
    public void pass(String details) {
        logger.info(details);
        report().log(LogStatus.PASS, details.replace("\n", "<br>"));
    }

    /**
     * show failed step in report
     * @param message
     */
    public void fail(String message) {
        logger.error(message);
        report().log(LogStatus.FAIL,  message);
    }

    public void failWithScreenshot(String details, Throwable e) {
        String exceptionString = getStackTrace(e);
        failWithScreenshot(details + "\n\n" + exceptionString);
    }

    /**
     * fail step and add screenshot
     * @param details
     */
    public void failWithScreenshot(String details) {
        String screenshotFile;
        String message = "<pre>" + details + "</pre>";
        logger.error(details);
        try {
            screenshotFile = FileIO.takeScreenshot(DriverProvider.getDriver());
            message = message + "<br><a href=\"" + screenshotFile + File.separator + "\" target=_blank alt>"
                    + "SCREENSHOT" + "</a><br>";
        } catch (Exception e){
            // processing of problem with taking screenshot
        }
        report().log(LogStatus.FAIL,  message);
    }

    /**
     * add step in report in follwoing format<br>
     *     table header1 - table header2<br>
     *     table1 item 1  table2 item 1<br>
     *     table1 item 2  table2 item 2<br>
     *     table1 item 3  table2 item 3<br>
     * @param table1Header
     * @param table2Header
     * @param table1Items
     * @param table2Items
     */
    public void addTableForComparison(String table1Header, String table2Header, List table1Items, List table2Items) {

        String message = "";

        message = "<div class='container'>\n" +
                "  <div class='row'>";

        message = message + "    <div class='col s5'>" +
                "  <h4>" + table1Header + "</h4>";

        for(Object itemObj : table1Items){
            String item = itemObj.toString();
            item = item.replaceAll("\n","<br>");
            if(item.matches(ReporterManager.MARKER_OF_FAILED_ITEM + ".*"))
                item = item.replace(MARKER_OF_FAILED_ITEM, "<div style='color:red'>") + "</div>";
            message = message + "<p>" + item.replaceAll("\n","<br>") + "</p>";
        }

        message = message + "</div>";

        message = message + "    <div class='col s2'>" +
                "  <h4>-</h4> </div>";

        message = message + "    <div class='col s5'>" +
                "  <h4>" + table2Header + "</h4>";

        for(Object itemObj : table2Items){
            String item = itemObj.toString();
            item = item.replaceAll("\n","<br>");
            if(item.matches(ReporterManager.MARKER_OF_FAILED_ITEM + ".*"))
                item = item.replace(MARKER_OF_FAILED_ITEM, "<div style='color:red'>") + "</div>";
            message = message + "<p>" + item + "</p>";
        }

        message = message + "</div>";

        message = message + "</div></div>";

        report().log(LogStatus.INFO, message);
    }

    //TODO under construction
    private String packMessage(String message) {
        String result = message;

        if (message.length() > 100) {
            String id = Tools.getCurDateTime() + Tools.getRandomNumber(99999);
                    result = "<script>function toggle" + id + "() { if (document.getElementById('" + id + "').style.display == 'block'){document.getElementById('" + id + "').style.display='none'; } else {document.getElementById('" + id + "').style.display='block'; }};</script><button onclick='toggle" + id + "()'>!!!</button> <button onClick=\"if (document.getElementById('" + id + "').style.display == 'block'){document.getElementById('" + id + "').style.display='none'; } else {document.getElementById('" + id + "').style.display='block'; }\"  > >> </button><br>" +
                            "<div id='" + id + "' style='display:block;word-wrap: break-word;border-style: solid;'>\n" +
                    message + "</div>";
        }

        return result;
    }


    /**
     * get trace as string
     * @param problem
     * @return
     */
    public static String getStackTrace(Throwable problem) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        problem.printStackTrace(printWriter);
        return result.toString();
    }

}