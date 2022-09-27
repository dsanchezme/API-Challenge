package controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.response.Response;

import java.util.Random;

public class PeopleController extends BaseController{

    public Response getPopularPeopleResponse(){
        String endpoint = "/person/popular";
        return makeGetRequest(endpoint);
    }

    public JsonArray getPopularPeopleList(){
        String responseAsString = getPopularPeopleResponse().asString();
        return JsonParser.parseString(responseAsString).getAsJsonObject().get("results").getAsJsonArray();
    }

    public JsonObject getPopularPerson(){
        JsonArray popularPeople = getPopularPeopleList();
        int selected = new Random().nextInt(popularPeople.size());
        return popularPeople.get(selected).getAsJsonObject();
    }

    public Response getPersonDetails(String personID){
        String endpoint = "/person/" + personID;
        return makeGetRequest(endpoint);
    }

    public Response getAllPersonImages(String personID){
        String endpoint = "/person/" + personID + "/images";
        return makeGetRequest(endpoint);
    }

}
