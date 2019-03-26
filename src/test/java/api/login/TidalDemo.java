package api.login;

import api.JobApiHelper;
import entities.BaseEntity;
import entities.Job;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.Diff;
import utils.FileIO;

import static utils.Tools.getCurDateTime;

public class TidalDemo extends BaseTest {

    @Test
    public void createJob() throws Exception {

        JobApiHelper apiHelper = new JobApiHelper(reporter);

        reporter.info("Getting a job object from a file");
        Job job = Job.getJobFromFile(FileIO.getTestDataFile("job.xml"));
        job.setName("apiTestJob " + getCurDateTime());

        reporter.info("Creating a job ("+ job.getName() +")");
        Response createJobResponse = apiHelper.createJob(job);

        reporter.info("Verifying that the job has been created successfully");
        Assert.assertTrue(createJobResponse.body().asString().contains("Job has been created"), createJobResponse.body().asString());

        String jobId = Job.getJobIdFromResponse(createJobResponse);

        Response getJobResponse = apiHelper.getJob(jobId);

        Job createdJob = Job.getJobsFromResponse(getJobResponse).get(0);

//        createdJob.setName("qwe");
//        createdJob.setActive("N");

        reporter.info("Verifying that all fields has been populated properly");
        Diff diff = BaseEntity.CompareEntities(job, createdJob);
        Assert.assertTrue(diff.isEmpty(), diff.asPrettyString());

        reporter.info("Deleting the job");
        Response deleteJobResponse = apiHelper.deleteJob(createdJob);

        reporter.info("Verifying that the job has been deleted successfully");
        Assert.assertTrue(deleteJobResponse.body().asString().contains("Job has been deleted"), deleteJobResponse.body().asString());
    }
}


