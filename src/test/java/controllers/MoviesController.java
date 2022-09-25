package controllers;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MoviesController extends BaseController{

    public Response getLatestMovie(){
        String endpoint = "/movie/latest";
        return makeGetRequest(endpoint);
    }

    public String getMovieID(){
        Response response = getLatestMovie();
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
