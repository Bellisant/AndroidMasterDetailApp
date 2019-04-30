package com.bellisant.simplelist;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String URL = "arctic2019.octopod.com/api/partners";
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

        // fetch partners
        new ReceivePartnersTask().execute();
        Log.v(TAG, "onCreate(): new ReceivePartnersTask().execute()");

    }

    private void attachMasterViewFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager
                .findFragmentById(R.id.place_fragment_here);

        if (fragment == null) {
            Log.v(TAG, "onCreate(): new MasterViewFragment created");

            fragment = new MasterViewFragment();
            supportFragmentManager.beginTransaction()
                    .add(R.id.place_fragment_here, fragment)
                    .commit();
        }
    }

    private class ReceivePartnersTask
            extends AsyncTask<Void, Void, List<Partner>> {

        private static final String TAG = "ReceivePartnersTask";

        @Override
        protected List<Partner> doInBackground(Void... params) {
            Log.v(TAG, "doInBackground(): fetching from " + URL);
            return new Receiver().fetchPartners(URL);
        }

        @Override
        protected void onPostExecute(List<Partner> items) {
            mPartners = items;
            attachMasterViewFragment();
            Log.v(TAG,
                    "onPostExecute(): pass fetched partners to  mPartners");
        }
    }
}
