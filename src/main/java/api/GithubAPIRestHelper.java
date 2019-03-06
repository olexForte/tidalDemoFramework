package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIO;
import utils.ReporterManager;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class GithubAPIRestHelper {

    private static final Logger logger = LoggerFactory.getLogger(GithubAPIRestHelper.class);

    String baseURL = FileIO.getConfigProperty("ApiUrl");

    ReporterManager reporter;

    public GithubAPIRestHelper(ReporterManager reporter){
        this.reporter = reporter;
    }

    /**
     * get all repositories for user
     * @param username
     * @return
     */
    public List<String> getAllRepositories(String username) {
        String URL = baseURL + "/users/" + username + "/repos";
        reporter.info("URL: " + baseURL );
        Response response = given()
                .when()
                .get(URL)
                .then()
                .extract()
                .response();

        reporter.info(response.prettyPrint());
        return response.jsonPath().get("name");
    }
}