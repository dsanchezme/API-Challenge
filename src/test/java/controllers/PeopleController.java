package controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PeopleController extends BaseController{

    private final Logger logger = LogManager.getLogger("people");

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

}
