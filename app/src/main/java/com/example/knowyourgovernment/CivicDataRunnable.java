package com.example.knowyourgovernment;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CivicDataRunnable implements Runnable {

    private static final String TAG = "CivicDataRunnable";

    private static final String API_KEY = "AIzaSyBvBei9vyfwsdII_G9EXyFzjRZFUR5jH1M";
    private MainActivity mainActivity;
    private String address;
    private String DATA_URL;

    // location vars
    private String city;
    private String state;
    private String zip;

    // stores information about provided areas political offices
    HashMap<Integer, String> offices = new HashMap();
    ArrayList<Official> officials = new ArrayList<>();

    CivicDataRunnable(MainActivity main, String addr) {
        this.mainActivity = main;
        this.address = addr;
    }

    @Override
    public void run() {
        // construct data url
        DATA_URL = "https://www.googleapis.com/civicinfo/v2/representatives?key=" +
                API_KEY +
                "&address=" +
                address;

        Uri dataUri = Uri.parse(DATA_URL);
        String usageURL = dataUri.toString();
        StringBuilder sb = new StringBuilder();

        try {
            // connect to url
            URL url = new URL(usageURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // check connection
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "run: HTTP ResponseCode NOT OK - not connect to data source.\nResponse: " +
                        conn.getResponseCode());
                //TODO: HANDLE NULL DATA HERE
                return;
            }

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (Exception e) {
            Log.d(TAG, "run: exception", e);
            //TODO: HANDLE NULL DATA HERE
            return;
        }

        // TODO: HANDLE DATA HERE
        dataHandler(sb.toString());
    }

    private void dataHandler(String s) {
        if (s == null) {
            Log.d(TAG, "dataHandler: Failure in downloading data.");
            mainActivity.runOnUiThread(() -> mainActivity.downloadFailed());
            return;
        }
        parseJSON(s);
        // TODO: POPULATE MAINACTIVITY USING DATA HERE
    }

    private void parseJSON(String s) {
        try {
            // jsonObject containing all of the returned api data
            JSONObject jsonObject = new JSONObject(s);

            // normalizedInput object data
            JSONObject normalizedInput = jsonObject.getJSONObject("normalizedInput");
            city = normalizedInput.getString("city");
            state = normalizedInput.getString("state");
            zip = normalizedInput.getString("zip");

            // offices array data
            JSONArray officesJson = jsonObject.getJSONArray("offices");
            for (int i = 0; i < officesJson.length(); i++) {
                JSONObject office = officesJson.getJSONObject(i);

                // get office name
                String officeName = office.getString("name");

                // storing office name keyed by the provided office indices
                JSONArray officialIndicesJSON = office.getJSONArray("officialIndices");
                for (int j = 0; j < officialIndicesJSON.length(); j++) {
                    offices.put(officialIndicesJSON.getInt(j), officeName);
                }
            }

            // officials array data
            JSONArray officialsJSON = jsonObject.getJSONArray("officials");
            for (int i = 0; i < officialsJSON.length(); i++) {

                JSONObject officialObject = officialsJSON.getJSONObject(i);
                Official o = new Official();

                // saving information for each official
                o.setName(officialObject.getString("name"));
                o.setTitle(offices.get(i));
                o.setParty(officialObject.getString("party"));

            }
        } catch (Exception e) {
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}