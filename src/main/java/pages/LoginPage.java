package pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by odiachuk on 2/25/19.
 */
public class LoginPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);
    private static LoginPage instance;
    public static LoginPage Instance = (instance != null) ? instance : new LoginPage();

    By loginField = By.xpath("//input[@id='login_field']");
    By passwordField = By.id("password");
    By loginButton = By.xpath("//input[@type='submit']");
    By errorMessage = By.cssSelector("div.flash.flash-full.flash-error");

    /**
     * enter login
     * @param login
     * @return
     */
    public LoginPage enterLogin(String login) {
        LOGGER.info("Login: " + login);
        setText(loginField, login);
        return this;
    }

    /**
     * enter password
     * @param password
     * @return
     */
    public LoginPage enterPassword(String password) {
        LOGGER.info("Passw0rd: " + password);
        setText(passwordField, password);
        return this;
    }

    /**
     * click Submit
     * @return
     */
    public LoginPage submitForm() {
        LOGGER.info("Click Login");
        clickOnElement(loginButton);
        return this;
    }

    /**
     * get error message
     * @return
     */
    public String getErrorMessage() {
        String result = "";
        try {
            result = findElementIgnoreException(errorMessage).getText();
            LOGGER.info("Message: " + result);
        } catch (Exception e) {
            // no error message found
        }

        return result;
    }

    /**
     * login to app
     * @param user
     * @param pass
     */
    public void login(String user, String pass) {
        open();

        enterLogin(user)
                .enterPassword(pass)
                .submitForm();

        waitForPageToLoad();
    }
}
