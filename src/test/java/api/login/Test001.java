package api.login;

import api.GithubAPIRestHelper;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.FileIO;
import utils.PropertiesList;
import utils.Tools;

import java.util.List;

/**
 * Created by odiachuk on 2/25/19.
 */
public class Test001 extends BaseTest{

    String userName = PropertiesList.getConfigProperty("AppLogin");
    List<String> expectedData = Tools.stringsToList(FileIO.getTestDataFileContent("Test001_List.txt"));

    @Test(testName = "API Get all repos and compare")
    public void test(){

        reporter.info("Get repositories list for " + userName);
        GithubAPIRestHelper rest = new GithubAPIRestHelper(reporter);
        List<String> actualData = rest.getAllRepositories(userName);
        Assert.assertTrue(actualData.containsAll(expectedData), "Actual data: " + actualData + " does not equal to Expected data: " + expectedData);
    }
}
