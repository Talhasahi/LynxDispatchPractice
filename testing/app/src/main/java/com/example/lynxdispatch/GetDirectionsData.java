package com.example.lynxdispatch;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetDirectionsData extends AsyncTask<Object, String, String> {

    String url;
    String googleDirectionsData;
    String duration, distance;

    @Override
    protected String doInBackground(Object... objects) {
        url = (String) objects[0];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s) {
        HashMap<String, String> directionsList = null;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(s);

        duration = directionsList.get("duration");
        distance = directionsList.get("distance");

    }
}
