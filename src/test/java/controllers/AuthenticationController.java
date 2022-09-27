package controllers;

import helpers.SystemVariables;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthenticationController extends BaseController{

    private final Logger logger = LogManager.getLogger("authentication");
    private final SystemVariables systemVariables = new SystemVariables();

    public Response createRequestToken(){
        String endpoint = "/authentication/token/new";
        return makeGetRequest(endpoint);
    }

    public String getRequestToken(){
        Response response = createRequestToken();
        logger.debug("Request token response: " + response.asString());
        return getValueFromResponse(response, "request_token");
    }

    public Response createSessionWithLogin(String requestToken){
        String endpoint = "/authentication/token/validate_with_login";
        String body = "{ \"username\": \"" + systemVariables.getUsername() + "\"," +
                        "\"password\": \"" + systemVariables.getPassword() + "\"," +
                        "\"request_token\": \"" + requestToken + "\" }";
        RequestSpecification spec = requestSpecification.body(body);
        return makePostRequest(endpoint, spec);
    }

    public Response createNewSession(String requestToken){
        String endpoint = "/authentication/session/new";
        createSessionWithLogin(requestToken);
        String body = "{ \"request_token\": \"" + requestToken + "\" }";
        RequestSpecification spec = requestSpecification.body(body);
        return makePostRequest(endpoint,  spec);
    }

    public String getSessionID(){
        Response response = createNewSession(getRequestToken());
        logger.debug("Create new session response: " + response.asString());
        return getValueFromResponse(response, "session_id");
    }

    public Response deleteSession(String sessionID){
        String endpoint = "/authentication/session";
        String body = "{ \"session_id\": \"" + sessionID + "\" }";
        RequestSpecification spec = requestSpecification.queryParam("session_id", sessionID).body(body);
        return makeDeleteRequest(endpoint, spec);
    }

}
