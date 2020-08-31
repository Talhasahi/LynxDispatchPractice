package com.example.lynxdispatch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class DataParser {
    public HashMap<String, String> parseDirections(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getDuration(jsonArray);
    }

    private HashMap<String, String> getDuration(JSONArray googleDirectionsJson) {

        HashMap<String, String> googleDirectionsMap = new HashMap<>();
        String duration = "";
        String distance = "";
        Log.d("json response", googleDirectionsJson.toString());

        try {
            duration = googleDirectionsJson.getJSONObject(0).getJSONObject("duration").getString("text");
            distance = googleDirectionsJson.getJSONObject(0).getJSONObject("distance").getString("text");

            googleDirectionsMap.put("duration", duration);
            googleDirectionsMap.put("distance", distance);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return googleDirectionsMap;

    }
}
