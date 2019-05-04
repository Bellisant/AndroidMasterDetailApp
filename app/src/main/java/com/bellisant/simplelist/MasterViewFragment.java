package com.bellisant.simplelist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MasterViewFragment extends Fragment {
    private static final String TAG = "MasterViewFragment";

    private ListView mListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        Log.v(TAG, "onCreateView(): get reference to listView");

        setAdapter();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment detailViewFragment = DetailViewFragment.getNewDetailViewFragment(position);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.place_fragment_here, detailViewFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setAdapter() {
        Log.v(TAG, "setAdapter():");

        PartnerAdapter partnerAdapter =
                new PartnerAdapter(getContext(), ((MainActivity)getActivity()).getPartners());

        mListView.setAdapter(partnerAdapter);
    }


}
