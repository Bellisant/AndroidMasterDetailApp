package com.bellisant.simplelist;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public final class Utils {
    private static final String LOG_TAG = Utils.class.getName();

    // it's a utility class and should't be instantiated
    private Utils() {
    }

    interface PartnerService {
        @GET("partners")
        Call<PartnersData> getPartnersData();
    }

    class PartnersData {
        @SerializedName("data")
        private List<PartnerCategory> data;

        public List<Partner> getAllPartners() {
            List<Partner> partners = new ArrayList<>();
            for (PartnerCategory pc : data) {
                partners.addAll(pc.getPartners());
            }
            return partners;
        }

        class PartnerCategory {
            @SerializedName("title")
            private String title;
            @SerializedName("link")
            private String link;
            @SerializedName("partners")
            private List<Partner> partners;

            public List<Partner> getPartners() {
                return partners;
            }
        }
    }
}
