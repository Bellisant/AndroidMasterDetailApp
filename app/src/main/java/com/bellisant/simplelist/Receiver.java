package com.bellisant.simplelist;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Receiver {
    private static final String TAG = "Receiver";

    // helper for handling exceptions
    public List<Partner> fetchPartners(String url) {
        List<Partner> partners = null;
        try {
            partners = fetchPartnersCollection(url);
        } catch (IOException e) {
            Log.v(TAG, "fetchPartners(): IOException");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.v(TAG, "fetchPartners(): JSONException");
            e.printStackTrace();
        }
        String msg = String.format(Locale.ENGLISH,
                "fetchPartners(): %d partners fetched",
                partners.size());
        Log.v(TAG, msg);

        return partners;
    }

    // return collection of Partner objects from specified url
    private List<Partner> fetchPartnersCollection(String url) throws IOException, JSONException {
        ArrayList<Partner> allPartners = new ArrayList<>();

        // fetch all text from url
        String jsonTextFromUrl = getUrlString(url);

        // create top level JSON object from this text
        JSONObject jsonBody = new JSONObject(jsonTextFromUrl);

        // take main array (= partners' categories)
        JSONArray arrayOfCategories = jsonBody.getJSONArray("data");

        for (int i = 0; i < arrayOfCategories.length(); i++) {

            // get category object
            JSONObject partnersCategory = arrayOfCategories.getJSONObject(i);

            // take array of json objects, representing partners
            JSONArray partnersInCategory =
                    partnersCategory.getJSONArray("partners");

            // create model objects (Partner) from json objects
            // and put them in collection
            for (int j = 0; j < partnersInCategory.length(); j++) {

                JSONObject partnerJSON = partnersInCategory.getJSONObject(j);
                Partner partner = new Partner();

                partner.setId(partnerJSON.getInt("id"));
                partner.setName(partnerJSON.getString("name"));
                partner.setDescription(partnerJSON.getString("description"));
                partner.setUrl(partnerJSON.getString("url"));
                partner.setType(partnerJSON.getString("type"));

                // not all partners have "place_id" and "domain"
                if (partnerJSON.has("place_id")) {
                    partner.setPlace_id(partnerJSON.getInt("place_id"));
                }
                if (partnerJSON.has("domain")) {
                    partner.setDomain(partnerJSON.getString("domain"));
                }
                if (partnerJSON.has("image")) {
                    partner.setImage(partnerJSON.getString("image"));
                }
                allPartners.add(partner);
            }
        }
        return allPartners;
    }

    // fetch data from specified url as byte array
    private static byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL("http://" + urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException();
            }
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    // make String from byte array
    private static String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
}
