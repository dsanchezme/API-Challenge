package controllers;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthenticationController extends BaseController{

    private static Logger logger = LogManager.getLogger("authentication");

    public Response createRequestToken(){
        String endPoint = "/authentication/token/new";
        logger.debug("Requesting to " + endPoint);
        return makeGetRequest(endPoint);
    }

}
