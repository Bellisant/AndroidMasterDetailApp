package com.bellisant.simplelist;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity
        extends AppCompatActivity
        implements MasterViewFragment.PartnerItemClicker {

    private static final String TAG = "MainActivity";
    public static final String URL = "http://arctic2019.octopod.com/api/";

    // collection of partner objects
    private List<Partner> mPartners;

    public Partner getPartner(int i) {
        return mPartners.get(i);
    }

    public List<Partner> getPartners() {
        return mPartners;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new PartnersDataLoader().loadPartnersData();
    }

    private void attachMasterViewFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.place_fragment_here);

        if (fragment == null) {
            Log.v(TAG, "onCreate(): new MasterViewFragment created");

            fragment = new MasterViewFragment();
            supportFragmentManager.beginTransaction()
                    .add(R.id.place_fragment_here, fragment)
                    .commit();
        }
    }

    private void attachDetailFragmentFromPostion(int position) {
        // create DetailViewFragment for showing partner from clicked item
        Fragment dvf = DetailViewFragment.getNewDetailViewFragment(position);

        // change attached to activity fragment on freshly created
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.place_fragment_here, dvf)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPartnerItemClick(int position) {
        attachDetailFragmentFromPostion(position);
    }

    private class PartnersDataLoader implements Callback<Utils.PartnersData> {
        public void loadPartnersData() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Utils.PartnerService service = retrofit.create(Utils.PartnerService.class);
            Call<Utils.PartnersData> partnersDataCall = service.getPartnersData();
            partnersDataCall.enqueue(this);
        }

        @Override
        public void onResponse(Call<Utils.PartnersData> call,
                               retrofit2.Response<Utils.PartnersData> response) {

            Utils.PartnersData partnersData = response.body();
            mPartners = partnersData.getAllPartners();
            attachMasterViewFragment();
        }

        @Override
        public void onFailure(Call<Utils.PartnersData> call, Throwable t) {

        }
    }
}
