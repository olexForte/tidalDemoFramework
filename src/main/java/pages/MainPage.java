package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by odiachuk on 2/25/19.
 */
public class MainPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainPage.class);
    private static MainPage instance;
    public static MainPage Instance = (instance != null) ? instance : new MainPage();

    By showMoreButton = By.cssSelector("button.width-full.text-left.btn-link.f6.muted-link.text-left.mt-2");

    /**
     * get all repositories from list
     * @return
     */
    public List<String> getAllRepositories(String username) {
        reporter.info("Get all repositories from list");
        ArrayList<String> result = new ArrayList<>();
        for(WebElement elem : findElements( By.xpath("(//div[@class='js-repos-container'])[1]//li//*[@title='" + username + "']/following::span[1]"))){
            LOGGER.info(elem.getText());
            result.add(elem.getText());
        }
        Collections.sort(result);
        return result;
    }

    /**
     * click on Show more Repositories button
     * @return
     */
    public MainPage clickShowMoreReporsitoriesButton(){
        reporter.info("Click ShowMore button");
        findElement(showMoreButton).click();
        waitForPageToLoad();
        return this;
    }

}
