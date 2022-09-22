package controllers;

import helpers.SystemVariables;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthenticationController extends BaseController{

    private static Logger logger = LogManager.getLogger("authentication");
    private SystemVariables systemVariables = new SystemVariables();

    public Response createRequestToken(){
        String endpoint = "/authentication/token/new";
        return makeGetRequest(endpoint);
    }

    public String getRequestToken(){
        Response response = createRequestToken();
        return getValueFromResponse(response, "request_token");
    }

    public Response createSessionWithLogin(String requestToken){
        String endpoint = "/authentication/token/validate_with_login";
        String body = "{ \"username\": \"" + systemVariables.getUsername() + "\"," +
                        "\"password\": \"" + systemVariables.getPassword() + "\"," +
                        "\"request_token\": \"" + requestToken + "\" }";
        return makePostRequest(endpoint,  body);
    }

    public Response createNewSession(String requestToken){
        String endpoint = "/authentication/session/new";
        createSessionWithLogin(requestToken);
        String body = "{ \"request_token\": \"" + requestToken + "\" }";
        return makePostRequest(endpoint,  body);
    }

    public String getSessionID(){
        Response response = createNewSession(getRequestToken());
        return getValueFromResponse(response, "session_id");
    }

    public Response deleteSession(String sessionID){
        String endpoint = "/authentication/session";
        String body = "{ \"session_id\": \"" + sessionID + "\" }";
        return makeDeleteRequest(endpoint,  body);
    }

}
