package api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ReporterManager;

import static utils.PropertiesList.getConfigProperty;

public class BaseApiHelper {

    protected static Logger logger;

    protected String baseURL = getConfigProperty("ApiUrl");

    protected ReporterManager reporter;

    protected String UserName = getConfigProperty("Username");

    protected String Password = getConfigProperty("Password");

    protected BaseApiHelper(ReporterManager reporter){
        this.reporter = reporter;
        logger = LoggerFactory.getLogger(this.getClass());
    }
}
