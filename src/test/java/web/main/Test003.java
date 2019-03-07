package web.main;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import utils.BaseUITest;
import utils.FileIO;
import utils.PropertiesList;
import utils.Tools;

import java.util.List;

/**
 * Created by odiachuk on 2/25/19.
 */
public class Test003 extends BaseUITest {

    String login = PropertiesList.getConfigProperty("AppLogin");
    String password = PropertiesList.getConfigProperty("AppPassword");
    List<String> expectedData = Tools.stringsToList(FileIO.getTestDataFileContent("Test001_List.txt"));

    @Test(testName = "Web Test003")
    public void login(){

        LoginPage loginPage = new LoginPage();
        loginPage.login(login, password);

        MainPage mp = new MainPage();

        List<String> actualData = mp.getAllRepositories(login);
        Assert.assertTrue(actualData.containsAll(expectedData), "Actual data: " + actualData + " does not equal to Expected data: " + expectedData);
    }
}
