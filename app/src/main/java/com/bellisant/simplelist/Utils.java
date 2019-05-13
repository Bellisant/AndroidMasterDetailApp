package com.bellisant.simplelist;

import android.text.TextUtils;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Contains static utility methods for fetching and parsing json data
 */
public final class Utils {
    private static final String LOG_TAG = Utils.class.getName();

    // it's a utility class and should't be instantiated
    private Utils() {
    }

    /**
     * Get JSON data from specified URL
     * and parse it to collection of {@link Partner}.
     * <p>
     * This method wraps {@link Utils#fetchPartnersList(String)}
     * and provide exception handling (wraps this ugly try-catch clauses)
     *
     * @param url url of source where json will be requested
     * @return collection of {@link Partner}
     */
    public static List<Partner> fetchPartners(String url) {
        List<Partner> partners = null;
        try {
            partners = fetchPartnersList(url);

        } catch (IOException ioe) {
            String msg = String.format("fetchPartners(): IOException : %s", ioe.getMessage());
            Log.v(LOG_TAG, msg);

        } catch (JSONException jsonEx) {
            String msg = String.format("fetchPartners(): JSONException : %s", jsonEx.getMessage());
            Log.v(LOG_TAG, msg);

        }
        String msg = String.format(
                Locale.ENGLISH,
                "fetchPartners(): %d partners fetched",
                partners.size());
        Log.v(LOG_TAG, msg);

        return partners;
    }


    /**
     * Get json from specified url
     * and parse it to collection of {@link Partner}.
     *
     * @param url json data to parsing
     * @return collection of {@link Partner}
     * @throws IOException
     * @throws JSONException
     */
    private static List<Partner> fetchPartnersList(String url) throws IOException, JSONException {
        ArrayList<Partner> allPartners = new ArrayList<>();
        // fetch all text from url
        String jsonTextFromUrl = getJSONString(url);
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


    /**
     * Make GET request to specified url and return body as String
     *
     * @param urlString url to request
     * @return String containing body of request
     */
    private static String getJSONString(String urlString) {
        String jsonString = "";
        if (TextUtils.isEmpty(urlString)) {
            Log.i(LOG_TAG, "getJSONString(): passed url is empty");
            return jsonString;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlString).build();
        try (Response response = client.newCall(request).execute()) {
            jsonString = response.body().string();
        } catch (IOException ioe) {
            Log.e(LOG_TAG, "getJSONString(): IOException while OkHttp make request");
        }
        return jsonString;
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
