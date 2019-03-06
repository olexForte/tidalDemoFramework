package web.login;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.BaseUITest;
import utils.BrowserProxy;
import utils.PropertiesList;

/**
 * Created by odiachuk on 2/25/19.
 */
public class loginWithProxy extends BaseUITest{

    String login = PropertiesList.getConfigProperty("AppLogin");
    String password = PropertiesList.getConfigProperty("AppPassword");

    @Test
    void loginWithRecording() {
        LoginPage loginPage = new LoginPage();

        loginPage.open();

        BrowserProxy.getInstance().createHARFileFromProxy("Login");

        loginPage
                .enterLogin(login)
                .enterPassword(password)
                .submitForm();

        Assert.assertTrue(loginPage.getErrorMessage().equals(""), "Unexpected message was found");
        BrowserProxy.getInstance().dumpHARFileFromProxy();
    }
}
