package api;

import entities.Job;
import io.restassured.response.Response;
import utils.ReporterManager;

import static io.restassured.RestAssured.given;

public class JobApiHelper extends BaseApiHelper{

    public JobApiHelper(ReporterManager reporterManager){
        super(reporterManager);
    }

    public Response createJob(Job obj) throws Exception {
        return given()
                .auth().basic(UserName, Password)
                .when().param("data", obj.createRequest())
                .post(baseURL)
                .then()
                .extract()
                .response();
    }

    public Response getJob(String id) throws Exception{
        return given()
                .auth().basic(UserName, Password)
                .when().param("data", Job.getJobRequest(id))
                .post(baseURL)
                .then()
                .extract()
                .response();
    }

    public Response deleteJob(Job obj) throws Exception{
        return given()
                .auth().basic(UserName, Password)
                .when().param("data", obj.deleteRequest())
                .post(baseURL)
                .then()
                .extract()
                .response();
    }
}
