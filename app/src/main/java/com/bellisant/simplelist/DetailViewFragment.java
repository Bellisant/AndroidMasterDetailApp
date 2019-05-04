package com.bellisant.simplelist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class DetailViewFragment extends Fragment {
    private static final String TAG = "DetailViewFragment";
    public static final String PARTNER_INDEX = "partnerIndex";
    private Partner mPartner;

    public static Fragment getNewDetailViewFragment(int partnerIndex) {
        DetailViewFragment fragment = new DetailViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PARTNER_INDEX, partnerIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView()");

        View root = inflater.inflate(R.layout.fragment_detail_view, container, false);
        mPartner = getPartnerByIndexFromArguments();
        setNameTextView(root);
        setTypeTextView(root);
        setDescriptionView(root);
        return root;
    }

    private void setTypeTextView(View view) {
        TextView typeTextView = view.findViewById(R.id.detail_fragment_type_text_view);
        typeTextView.setText(mPartner.getType());
    }

    private void setNameTextView(View view) {
        TextView nameTextView = view.findViewById(R.id.detail_fragment_name_text_view);
        nameTextView.setText(mPartner.getName());
    }

    private void setDescriptionView(View view) {
        TextView tv = view.findViewById(R.id.details_fragment_description);
        tv.setText(Html.fromHtml(mPartner.getDescription()));
    }

    private Partner getPartnerByIndexFromArguments() {
        int partnerIndex = getArguments().getInt(PARTNER_INDEX);
        return ((MainActivity) getActivity()).getPartner(partnerIndex);
    }
}
