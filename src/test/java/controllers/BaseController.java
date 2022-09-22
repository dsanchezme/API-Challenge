package controllers;

import helpers.SystemVariables;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;

public class BaseController {

    private final SystemVariables systemVariables = new SystemVariables();
    private static final Logger logger = LogManager.getLogger("base");

    private final String baseURL = "https://api.themoviedb.org/3";

    @BeforeSuite
    public void setup(){
        RestAssured.baseURI = "https://api.themoviedb.org/3";
        logger.info("Setting up baseURI");
    }

    protected Response makeGetRequest(String path){
        path = baseURL + path;
        logger.debug("Get request to " + path);
        return RestAssured.given().contentType(ContentType.JSON)
                .queryParam("api_key", systemVariables.getApiKey())
                .when().get(path)
                .then().extract().response();
    }

    protected Response makePostRequest(String path, String requestBody){
        path = baseURL + path;
        logger.debug("Post request to " + path + " with body " + requestBody);
        return RestAssured.given().contentType("application/json")
                .queryParam("api_key", systemVariables.getApiKey())
                .body(requestBody)
                .when().post(path)
                .then().extract().response();
    }

    protected Response makeDeleteRequest(String path, String requestBody){
        path = baseURL + path;
        logger.debug("Delete request to " + path + " with body " + requestBody);
        return RestAssured.given().contentType("application/json")
                .queryParam("api_key", systemVariables.getApiKey())
                .body(requestBody)
                .when().delete(path)
                .then().extract().response();
    }

    public static String getValueFromResponse(Response response, String key){
        String value = null;
        try {
            value = response.jsonPath().get(key);
        }catch (Exception e){
            logger.warn("Unable to get " + key + " from response");
        }
        return value;
    }
}
