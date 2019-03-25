package api.login;

import entities.BaseEntity;
import entities.Job;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.Diff;
import static io.restassured.RestAssured.given;
import static utils.Tools.getCurDateTime;

public class TidalDemo extends BaseTest {

    @Test
    public void getJobsList() throws Exception {

        Job job = Job.getJobFromFile("./src/test/resources/job.xml");
        job.setName("apiTestJob " + getCurDateTime());

        Response createJobResponse = createJob(job);
        Assert.assertTrue(createJobResponse.body().asString().contains("Job has been created"));

        String jobId = Job.getJobIdFromResponse(createJobResponse);

        Response getJobResponse = getJob(jobId);

        Job createdJob = Job.getJobsFromResponse(getJobResponse).get(0);

//        createdJob.setName("qwe");
//        createdJob.setActive("N");

        Diff diff = BaseEntity.CompareEntities(job, createdJob);
        Assert.assertTrue(diff.isEmpty(), diff.prettyString());

        Response deleteJobResponse = deleteJob(createdJob);
        Assert.assertTrue(deleteJobResponse.body().asString().contains("Job has been deleted"));
    }

    private Response createJob(Job obj) throws Exception {
        return given()
                .auth().basic("dv\\tes", "control01")
                .when().param("data", obj.createRequest())
                .post("http://172.21.243.103:8080/api/tes-6.3/post")
                .then()
                .extract()
                .response();
    }

    private Response getJob(String id) throws Exception{
        return given()
                .auth().basic("dv\\tes", "control01")
                .when().param("data", Job.getJobRequest(id))
                .post("http://172.21.243.103:8080/api/tes-6.3/post")
                .then()
                .extract()
                .response();
    }

    private Response deleteJob(Job obj) throws Exception{
        return given()
                .auth().basic("dv\\tes", "control01")
                .when().param("data", obj.deleteRequest())
                .post("http://172.21.243.103:8080/api/tes-6.3/post")
                .then()
                .extract()
                .response();
    }
}


