package controllers;

import helpers.SystemVariables;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
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
    
    protected RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addQueryParam("api_key", systemVariables.getApiKey())
                .build();

    protected Response makeGetRequest(String endpoint){
        endpoint = baseURL + endpoint;
        return RestAssured
                .given().spec(requestSpecification).body("")
                .when().get(endpoint)
                .then().extract().response();
    }

    protected Response makePostRequest(String endpoint, RequestSpecification specification){
        endpoint = baseURL + endpoint;
        return RestAssured
                .given().spec(specification)
                .when().post(endpoint)
                .then().extract().response();
    }
    protected Response makeDeleteRequest(String endpoint, RequestSpecification specification){
        endpoint = baseURL + endpoint;
        return RestAssured
                .given().spec(specification)
                .when().delete(endpoint)
                .then().extract().response();
    }

    public static String getValueFromResponse(Response response, String key){
        Object value = null;
        try {
            value = response.jsonPath().get(key);
        }catch (Exception e){
            logger.warn("Unable to get " + key + " from response");
            logger.warn(e);
        }
        return String.valueOf(value);
    }
}
