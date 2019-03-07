package web.login;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.BaseUITest;
import utils.PropertiesList;

/**
 * Created by odiachuk on 2/25/19.
 */
public class Test001 extends BaseUITest{

    String login = PropertiesList.getConfigProperty("AppLogin");
    String password = PropertiesList.getConfigProperty("AppPassword");

    @Test(testName = "Web Test001")
    public void login() {
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage
                .enterLogin(login)
                .enterPassword(password)
                .submitForm();

        Assert.assertTrue(loginPage.getErrorMessage().equals(""), "Unexpected message was found");
    }
}
