package web.login;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.BaseUITest;
import utils.PropertiesList;

/**
 * Created by odiachuk on 2/25/19.
 */
public class Test002 extends BaseUITest {

    @DataProvider(name ="dataProvider")
    public Object[][] returnTestData(){
        return new Object[][] {
                {"wrong username", PropertiesList.getConfigProperty("AppPassword"), "Incorrect username or password."},
                {PropertiesList.getConfigProperty("AppLogin"), "wrong password", "Incorrect username or password."},
                {"", "",  "Blank username or password."}

        };
    }

    @Test(dataProvider = "dataProvider",testName = "Web Test002")
    public void login(String user, String pass, String expectedError){

        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage
                .enterLogin(user)
                .enterPassword(pass)
                .submitForm();

        Assert.assertEquals( loginPage.getErrorMessage(), expectedError, "Expected message was not found");

    }
}
