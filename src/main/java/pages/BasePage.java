package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PropertiesList;
import utils.ReporterManager;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Base class for Page objects.
 */
public class BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);
    static public ReporterManager reporter = ReporterManager.Instance;
    
    private static int DEFAULT_TIMEOUT          = 15;
    private static int DEFAULT_SHORT_TIMEOUT    = 3000;
    private static int DEFAULT_STATIC_TIMEOUT   = 3000;

    public final static String BASE_URL = (PropertiesList.getConfigProperty("Environment"));

    public String pageURL = "";
    public String pageTitle = "";

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static final int MAIN_TIMEOUT = getTimeout();
    public static final int SHORT_TIMEOUT = getShortTimeout();
    public static final int STATIC_TIMEOUT =  getStaticTimeout();

    private static int getTimeout() {
        String timeout = PropertiesList.getConfigProperty("DefaultTimeoutInSeconds");
        if (timeout == null ) {
            reporter.fail("DefaultTimeoutInSeconds parameter was not found");
            return DEFAULT_TIMEOUT;
        };

        return Integer.parseInt(timeout);
    }

    private static int getShortTimeout() {
        String timeout = PropertiesList.getConfigProperty("ShortTimeoutInSeconds");
        if (timeout == null ) {
            return DEFAULT_SHORT_TIMEOUT;
        };

        return Integer.parseInt(timeout);
    }

    private static int getStaticTimeout() {
        String timeout = PropertiesList.getConfigProperty("StaticTimeoutMilliseconds");
        if (timeout == null ) {
            return DEFAULT_STATIC_TIMEOUT;
        };
        return Integer.parseInt(timeout);
    }

    public BasePage() {
        // waitForPageToLoad();
    }

    public static WebDriver driver(){
        return driver.get();
    }

    /**
     * Checks if the page is loaded by verifying it's title and url.
     */
    public boolean isPageLoaded() {
        boolean result = false;
        reporter.info("Page title is: " + driver().getTitle());
        reporter.info("Page URL is: " + driver().getCurrentUrl());
        if (driver().getTitle().contains(pageTitle))
            result = true;
        else {
            reporter.info("Expected title: " + pageTitle);
            result = false;
        }

        if (driver().getCurrentUrl().contains(pageURL))
            result = true;
        else {
            reporter.info("Expected URL: " + pageURL);
            result = false;
        }

        return result;
    }

    /**
     * Reloads the current page.
     */
    public void reloadPage() {
        driver().navigate().refresh();
    }

    /**
     * Navigates to the page url.
     */
    public void open() {

        reporter.info("Opening the page: " + "\"" + BASE_URL + pageURL + "\"");
        driver().get(BASE_URL + pageURL);
        //driver().manage().window().maximize();
    }

    /**
     * Closes the browser.
     */
    public void close() {
        reporter.info("Closing the browser");
        driver().close();
    }

    /**
     * Returns the page title.
     */
    public String getTitle() {
        reporter.info("The page title is: " + "\"" + pageTitle + "\"");
        return pageTitle;
    }

    /**
     * Returns page url.
     */
    public String getURL() {
        reporter.info("The requested URL is: " + BASE_URL + pageURL);
        return BASE_URL + pageURL;
    }

    /**
     * Sets the text to web element by locator.
     * @param element locator
     * @param value text
     */
    public void setText(By element, String value){
        if (value != null) {
            findElement(element).clear();
            findElement(element).sendKeys(value);
        }
    }

    /**
     * Checks if page source contains required text.
     */
    public boolean isTextPresent(String text) {
        return driver().getPageSource().contains(text);
    }

    /**
     * Checks if Element is present on the page.
     */
    public boolean isElementPresent(By by) {
        try {
            WebElement element = findElementIgnoreException(by);
            return element != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if Element is displayed on the page.
     */
    public boolean isElementDisplayed(By by) {
        try {
            return findElementIgnoreException(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns web element by selector.
     */
    public WebElement getWebElement(By by) {
        return findElement(by);
    }

    /**
     * Selects required item from the drop-down
     * @param element drop-down element
     * @param value item to be selected
     */
    public void selectFromDropdown(By element, String value){
        Select dropdown = new Select(findElement(element));
        dropdown.selectByVisibleText(value);
    }

    /**
     * Clicks on web element ignoring all exceptions.
     */
    public void clickOnElementIgnoreException(By element, int... timeout) {
        waitForPageToLoad();
        int timeoutForFindElement = timeout.length < 1 ? MAIN_TIMEOUT : timeout[0];
        try {
            (new WebDriverWait(driver(), timeoutForFindElement))
                    .until(ExpectedConditions.visibilityOfElementLocated(element));
            driver().findElement(element).click();
        } catch (Exception e) {
            // nothing
        }
        waitForPageToLoad();
    }

    /**
     * Finds required element by selector ignoring all exceptions.
     * @return web element / null if not found.
     */
    public WebElement findElementIgnoreException(By element, int... timeout) {
        //waitForPageToLoad();
        int timeoutForFindElement = timeout.length < 1 ? MAIN_TIMEOUT : timeout[0];
        waitForPageToLoad();
        try {
            //synchronize();
            (new WebDriverWait(driver(), timeoutForFindElement))
                    .until(ExpectedConditions.visibilityOfElementLocated(element));
            return driver().findElement(element);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Finds required elements by selector ignoring all exceptions.
     * @return web element list / empty list if not found.
     */
    public List<WebElement> findElementsIgnoreException(By element, int... timeout) {
        int timeoutForFindElement = timeout.length < 1 ? MAIN_TIMEOUT : timeout[0];
        //waitForPageToLoad();
        try {
            //synchronize();
//            (new WebDriverWait(driver(), timeoutForFindElement))
//                    .until(ExpectedConditions.presenceOfElementLocated(element));
            return driver().findElements(element);
        } catch (Exception e) {
            //reporter.info("Got exception. Exception is expected and ignored.");
            return new ArrayList<WebElement>();
        }
    }

    /**
     * Clicks on element by locator.
     */
    public void clickOnElement(By element, int... timeout) {
        int timeoutForFindElement = timeout.length < 1 ? MAIN_TIMEOUT : timeout[0];
        //waitForPageToLoad();
//        try {
//            (new WebDriverWait(driver(), timeoutForFindElement))
//                    .until(ExpectedConditions.presenceOfElementLocated(element)).click();
//        } catch (Exception e) {
//            reporter.failWithScreenshot(ReporterManager.getStackTrace(e));
//            throw new RuntimeException("Failure clicking on element: " + e.getMessage() );
//        }


        for (int i = 0 ; i < MAIN_TIMEOUT; i++ ) {
           try{
               driver().findElement(element).click();
               return;
        } catch (Exception e){
            if(i == MAIN_TIMEOUT) {
                reporter.failWithScreenshot(ReporterManager.getStackTrace(e));
                throw new RuntimeException("Failure clicking on element: " + e.getMessage() );
            }
            sleepFor(1000);
        }

    }

        waitForPageToLoad();
    }

    public WebElement findElement(By element, int... timeout) {
        int timeoutForFindElement = timeout.length < 1 ? MAIN_TIMEOUT : timeout[0];
        //waitForPageToLoad();
        try {
            //synchronize();
//            (new WebDriverWait(driver(), timeoutForFindElement))
//                    .until(ExpectedConditions.visibilityOfElementLocated(element));
            return driver().findElement(element);
        } catch (Exception e) {
            reporter.failWithScreenshot(ReporterManager.getStackTrace(e));
            throw new RuntimeException("Failure finding element");
        }
    }

    public List<WebElement> findElements(By element, int... timeout) {
        int timeoutForFindElement = timeout.length < 1 ? MAIN_TIMEOUT : timeout[0];
        //waitForPageToLoad();
        try {
            //synchronize();
//            (new WebDriverWait(driver(), timeoutForFindElement))
//                    .until(ExpectedConditions.presenceOfElementLocated(element));
            return driver().findElements(element);
        } catch (Exception e) {
            reporter.failWithScreenshot(ReporterManager.getStackTrace(e));
            throw new RuntimeException("Failure finding elements");
        }
    }

    /**
     * Scroll to element using javascript.
     */
    public void scrollToElement(WebElement element) {
        waitForPageToLoad();
        ((JavascriptExecutor) driver()).executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * wait until page is completely loaded
     */
    public void waitForPageToLoad() {
        sleepFor(STATIC_TIMEOUT); // todo fixme
        ExpectedCondition<Boolean> expectationReadyState = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver()).executeScript("return document.readyState")
                        .equals("complete");
            }
        };

        Wait<WebDriver> waitReadyState = new WebDriverWait(driver(), MAIN_TIMEOUT);

        try {
            if(!waitReadyState.until(expectationReadyState))
                reporter.info("JavaScript readyState query timeout - The page has not finished loading");
        } catch (Exception error) {
            //reporter.failWithScreenshot("JavaScript readyState query timeout - The page has not finished loading");
            reporter.info("The page has not finished loading: " + error.getMessage());
        }

    }

    public boolean waitForElement(By by){
        //WebDriverWait wait = new WebDriverWait(driver(), MAIN_TIMEOUT);
        //wait.until(ExpectedConditions.presenceOfElementLocated(by));
        boolean result = false;
        for(int i = 0; i < MAIN_TIMEOUT; i++){
            if(!isElementDisplayed(by)){
                sleepFor(1000);
            } else {
                return true;
            }
        }
        return result;
    }

    public void sleepFor(int timeout){
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
        }
    }

    void waitForAlert(WebDriver driver, int timeout) {
        int i = 0;
        while (i++ < timeout) {
            try {
                Alert alert = driver.switchTo().alert();
                break;
            } catch (NoAlertPresentException e)  // wait for second
            {
                sleepFor(1);
                continue;
            }
        }
    }


    // Does not work because of geckodriver bug - https://stackoverflow.com/questions/40360223/webdriverexception-moveto-did-not-match-a-known-command
    public void hoverItem(By element){
        reporter.info("Put mouse pointer over element: " + element.toString());
        Actions action = new Actions(driver());
        action.moveToElement(findElement(element)).build().perform();
    }

    public void switchToFrame(By xpath) {
        reporter.info("Switch to frame: " + xpath.toString());
        driver().switchTo().frame(findElement(xpath));
    }

    public void switchToDefaultContent(){
        reporter.info("Switch to default content");
        driver().switchTo().defaultContent();
    }

    /**
     * scroll page to element by xPath
     * @param xPath    xPath of element
     */
    public void scrollToElement(String xPath) {
        try {
            ((JavascriptExecutor) driver()).executeScript(
                    "var element = document.evaluate(\"" + xPath + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                            "element.scrollIntoView();window.scrollBy(0,-450)"
            );
        }catch (Exception e){
            e.getMessage();
        }
    }

    /**
     * click on Horizontal scroller element to move scroller
     * @param locator
     * @param scrollRight
     */
    public void moveHorizontalScroller(By locator, boolean scrollRight){
        Actions act = new Actions(driver());
        WebElement element = driver().findElement(locator);
        act.click(element).
                sendKeys(Keys.ARROW_RIGHT).
                sendKeys(Keys.ARROW_RIGHT).
                sendKeys(Keys.ARROW_RIGHT).
                sendKeys(Keys.ARROW_RIGHT).
                sendKeys(Keys.ARROW_RIGHT).
                sendKeys(Keys.ARROW_RIGHT).
                sendKeys(Keys.ARROW_RIGHT).
                sendKeys(Keys.ARROW_RIGHT).
                sendKeys(Keys.ARROW_RIGHT).
                sendKeys(Keys.ARROW_RIGHT)
        .build().perform();
    }

    /**
     * get title of page
     * @return      page title
     */
    public String getPageTitle() {
        String title = findElement(By.xpath("//div[@id='mainTitle']/h1")).getText();
        String smallText = "";
        try{
            smallText = findElement(By.xpath("//div[@id='mainTitle']/h1/small")).getText();
        } catch (Exception e) {
            return title;   //if title on page without small text
        }

        return title.replace(smallText, "").trim();
    }

    /**
    * Open next tab
    */
    public void openNextTab() {
        boolean idWasFoundOnPreviousIteration = false;
        String currentWindowID = driver().getWindowHandle();
        Object[] allWindowIDS = driver().getWindowHandles().toArray();
        for(Object id : allWindowIDS){
            if(idWasFoundOnPreviousIteration){
                driver().switchTo().window((String)id);
                return;
            }
            if ((id).equals(currentWindowID)){
                idWasFoundOnPreviousIteration = true;
            }
        }
        LOGGER.error("Next tab was not open");
    }

    /**
    * check is new tab was opened
    * @return   boolean value is new tab is opened
    */
    public boolean isNewTabOpened() {
        String currentWindowID = driver().getWindowHandle();
        Object[] allWindowIDS = driver().getWindowHandles().toArray();

        int currentWindowIndex = Arrays.asList(allWindowIDS).indexOf(currentWindowID);
        try {
            Object nextTabtWindowID = allWindowIDS[currentWindowIndex + 1];
            return true;
        } catch (Exception e) {
            LOGGER.error("Next tab was not opened");
            return false;
        }
    }

    /**
     * check is WebElement has specific class
     * @param element       WebElement which has to be verified
     * @param className     class that is being sought
     * @return              boolean value
     */
    public boolean isHasCertainClass(WebElement element, String className) {
        String[] classes = element.getAttribute("class").split( " ");
        for (String elementClass : classes) {
            if (elementClass.equals(className)) {
                return true;
            }
        }

        return false;
    }

    /**
     * go on previous page
     */
    public void goBack(){
        driver().navigate().back();
    }

    /**
     *
     * @param element
     * @return
     */
    public boolean waitForElementToDisappear(By element) {
        boolean result = false;
        for(int i = 0; i < MAIN_TIMEOUT; i++){
            if(isElementDisplayed(element)){
                sleepFor(1000);
            } else {
                return true;
            }
        }
        return result;
    }

    /**
     * click on element using JS
     * @param element
     */
    public void clickOnElementUsingJS(WebElement element) {
        ((JavascriptExecutor) driver()).executeScript("arguments[0].click()",element);
    }

    public void clickOnElementRightButton(By element) {
        waitForPageToLoad();
        try {
            (new WebDriverWait(driver(), MAIN_TIMEOUT))
                    .until(ExpectedConditions.visibilityOfElementLocated(element));

            Actions action= new Actions(driver());
            action.contextClick(driver().findElement(element)).build().perform();
        } catch (Exception e) {
            // nothing
        }
        waitForPageToLoad();
    }
}
