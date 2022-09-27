package controllers;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoviesController extends BaseController{

    private final Logger logger = LogManager.getLogger("movies");
    public Response getLatestMovie(){
        String endpoint = "/movie/latest";
        return makeGetRequest(endpoint);
    }

    public String getMovieID(){
        Response response = getLatestMovie();
        logger.debug("Get latest movie response: " + response.asString());
        return getValueFromResponse(response, "id");
    }

    public Response getMovieDetails(String movieID){
        String endpoint = "/movie/" + movieID;
        return makeGetRequest(endpoint);
    }

    public Response rateMovie(String sessionID, String movieID, float rateValue){
        String endpoint = "/movie/" + movieID + "/rating";
        String body = "{ \"value\": " + rateValue + " }";
        RequestSpecification spec = requestSpecification.queryParam("session_id", sessionID).body(body);
        return makePostRequest(endpoint, spec);
    }
}
