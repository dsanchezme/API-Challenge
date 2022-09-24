package utils;

import com.google.gson.Gson;

import java.util.Map;

public class BodyFactory {

    public static String mapToJson(Map<String, String> data){
        return new Gson().toJson(data);
    }
}
