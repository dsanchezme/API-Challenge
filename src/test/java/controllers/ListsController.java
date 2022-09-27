package controllers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class ListsController extends BaseController{

    private final Logger logger = LogManager.getLogger("lists");
    public Response createMovieList(String sessionID, String body){
        String endpoint = "/list";
        RequestSpecification spec = requestSpecification.queryParam("session_id", sessionID).body(body);
        return makePostRequest(endpoint, spec);
    }

    public String getListID(String sessionID){
        Random random = new Random();
        String body = "{ \"name\": \"" + "My new list" + "\"," +
                        "\"description\": \"" + "My list description has a random float number :) " + random.nextFloat()  + "\"," +
                        "\"language\": \"en\" }";
        Response response = createMovieList(sessionID, body);
        logger.debug("Create movie list response: " + response.asString());
        return getValueFromResponse(response, "list_id");
    }

    public Response getListDetails(String listID){
        String endpoint = "/list/" + listID;
        return makeGetRequest(endpoint);
    }

    public Response addMediaToList(String sessionID, String mediaID, String listID){
        String endpoint = "/list/" + listID + "/add_item";
        String body = "{ \"media_id\": " + mediaID + " }";
        RequestSpecification spec = requestSpecification.queryParam("session_id", sessionID).body(body);
        return makePostRequest(endpoint, spec);
    }

    public Response clearList(String sessionID, String listID){
        String endpoint = "/list/" + listID + "/clear";
        RequestSpecification spec = requestSpecification
                .queryParam("session_id", sessionID)
                .queryParam("confirm", true);

        return makePostRequest(endpoint, spec);
    }


    public Response deleteList(String sessionID, String listID) {
        String endpoint = "/list/" + listID;
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addQueryParam("session_id", sessionID).build();
        return makeDeleteRequest(endpoint, requestSpecification);
    }
}
