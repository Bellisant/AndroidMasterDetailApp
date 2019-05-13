package com.bellisant.simplelist;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String URL = "http://arctic2019.octopod.com/api/partners";

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

        // fetch partners
        new ReceivePartnersTask().execute();
        Log.v(TAG, "onCreate(): new ReceivePartnersTask().execute()");

    }

    /**
     * Fragment which will be shown at app startup
     */
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

    /**
     * Background task of downloading json data
     * and creating list of {@link Partner} objects.
     *
     * Result will be passed to {@link MainActivity}
     */
    private class ReceivePartnersTask
            extends AsyncTask<Void, Void, List<Partner>> {

        private static final String TAG = "ReceivePartnersTask";

        @Override
        protected List<Partner> doInBackground(Void... params) {
            Log.v(TAG, "doInBackground(): fetching from " + URL);
            return Utils.fetchPartners(URL);
        }

        @Override
        protected void onPostExecute(List<Partner> items) {
            // put fetched and created objects in collection within activity
            mPartners = items;

            // go further with UI works
            attachMasterViewFragment();

            Log.v(TAG, "onPostExecute(): " +
                    "pass fetched partners to mPartners");
        }
    }
}
