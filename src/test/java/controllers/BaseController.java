package controllers;

import helpers.SystemVariables;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseController {

    private final SystemVariables systemVariables = new SystemVariables();
    private static final Logger logger = LogManager.getLogger("base");

    private final String baseURL = "https://api.themoviedb.org/3";
    
    protected RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addQueryParam("api_key", systemVariables.getApiKey())
                .build();

    protected Response makeGetRequest(String endpoint){
        endpoint = baseURL + endpoint;
        logger.debug("Get request to " + endpoint);
        return RestAssured
                .given().spec(requestSpecification).body("")
                .when().get(endpoint)
                .then().extract().response();
    }

    protected Response makePostRequest(String endpoint, RequestSpecification specification){
        endpoint = baseURL + endpoint;
        logger.debug("Post request to " + endpoint);
        return RestAssured
                .given().spec(specification)
                .when().post(endpoint)
                .then().extract().response();
    }
    protected Response makeDeleteRequest(String endpoint, RequestSpecification specification){
        endpoint = baseURL + endpoint;
        logger.debug("Delete request to " + endpoint);
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
            logger.error(e);
        }
        return String.valueOf(value);
    }
}
