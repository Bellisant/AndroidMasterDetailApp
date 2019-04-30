package com.bellisant.simplelist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailViewFragment extends Fragment {
    private static final String TAG = "DetailViewFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.v(TAG, "onCreateView()");

        View view = inflater.inflate(
                R.layout.fragment_detail_view,
                container,
                false);

        return view;
    }
}
