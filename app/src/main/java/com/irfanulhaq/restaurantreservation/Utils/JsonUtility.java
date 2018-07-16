package com.irfanulhaq.restaurantreservation.Utils;

import android.view.View;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Ints;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.irfanulhaq.restaurantreservation.mvp.models.BaseMvpPModel;
import com.irfanulhaq.restaurantreservation.mvp.models.CustomerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonUtility {
    static Gson defaultGson = new Gson();
    static ObjectMapper mapper = new ObjectMapper();

    public static <T> String fromListToJsonString(List<T> list) {
        return defaultGson.toJson(list);
    }

    public static <T> String toJson(T t) {
        return defaultGson.toJson(t);
    }

    public static <T> T fromJsonString(Class<T> clazz, String jsonString) {
        return defaultGson.fromJson(jsonString, clazz);
    }

    public static <T> List<T> toListFromJsonString(Class<T> clazz, String json) throws IOException {
        JavaType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        ArrayList<T> readValue = mapper.readValue(json, collectionType);
        return readValue;
    }

    public static <T> String toJsonArray(List<T> keys,T value){
        JSONArray JSONarray = new JSONArray();
            for(T key: keys){
                try {
                  JSONObject jsonObject =   new JSONObject();
                  jsonObject.put(key.toString(),value);
                    JSONarray.put(jsonObject);
                }catch (JSONException ex){ }
            }
            return JSONarray.toString();
    }
    public static <T,V> String toJsonFromHashMap(HashMap<T,V> keyValuePairs){
       return defaultGson.toJson(keyValuePairs);
    }
    public static <T,V> HashMap<T,V> toHashMap(Class<T> kyClass,Class<V> vlClass,String jsonString){
        Type typeOfHashMap = new TypeToken<HashMap<T, V>>() { }.getType();
        return defaultGson.fromJson(jsonString, typeOfHashMap);
    }

}
