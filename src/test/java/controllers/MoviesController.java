package controllers;

import io.restassured.response.Response;

public class MoviesController extends BaseController{

    public Response getLatestMovie(){
        String endpoint = "/movie/latest";
        return makeGetRequest(endpoint);
    }

    public String getMovieID(){
        Response response = getLatestMovie();
        return getValueFromResponse(response, "id");
    }
}
