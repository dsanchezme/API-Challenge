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
    private static final Logger logger = LogManager.getLogger(BaseController.class);

    private final String baseURL = "https://api.themoviedb.org/3";

    @BeforeSuite
    public void setup(){
        RestAssured.baseURI = "https://api.themoviedb.org/3";
        logger.info("Setting up baseURI");
    }

    protected Response makeGetRequest(String path){
        path = baseURL + path;
        return RestAssured.given().contentType(ContentType.JSON)
                .param("api_key", systemVariables.getApiKey())
                .when().get(path)
                .then().extract().response();
    }

    protected Response makePostRequest(String path, String requestBody){
        path = baseURL + path;
        return RestAssured.given().contentType("application/json")
                .param("api_key", systemVariables.getApiKey())
                .body(requestBody)
                .when().post(path)
                .then().extract().response();
    }

    protected Response makeDeleteRequest(String path, String requestBody){
        path = baseURL + path;
        return RestAssured.given().contentType("application/json")
                .param("api_key", systemVariables.getApiKey())
                .body(requestBody)
                .when().delete(path)
                .then().extract().response();
    }

}
