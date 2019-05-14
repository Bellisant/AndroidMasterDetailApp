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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

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
        setUrlTextView(root);
        setDescriptionView(root);
        setLogoView(root);
        return root;
    }

    private void setLogoView(View root) {
        ImageView logo = root.findViewById(R.id.detail_logo);
        Picasso.Builder builder = new Picasso.Builder(getContext());
        builder.downloader(new OkHttp3Downloader(getContext()));
        builder.build()
                .load(mPartner.getImage())
//                .networkPolicy(NetworkPolicy.NO_STORE)
                .fit()
                .into(logo);

    }

    private void setUrlTextView(View view) {
        TextView urlTextView = view.findViewById(R.id.detail_fragment_url_text_view);
        urlTextView.setText(mPartner.getDomain());
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
