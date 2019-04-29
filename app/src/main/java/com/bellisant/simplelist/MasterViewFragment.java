package com.bellisant.simplelist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MasterViewFragment extends Fragment {
    public static final String URL = "arctic2019.octopod.com/api/partners";

    private List<Partner> mPartners;
    private ListView mListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ReceivePartnersTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_master_view,
                container,
                false);

        mListView = view.findViewById(R.id.list);

        return view;
    }

    private void setAdapter() {
        PartnerAdapter partnerAdapter =
                new PartnerAdapter(getContext(), mPartners);

        mListView.setAdapter(partnerAdapter);
    }

    private class ReceivePartnersTask extends AsyncTask<Void,Void,List<Partner>> {
        @Override
        protected List<Partner> doInBackground(Void... params) {
            return new Receiver().fetchPartners(URL);
        }

        @Override
        protected void onPostExecute(List<Partner> items) {
            mPartners = items;
            setAdapter();
        }
    }
}
