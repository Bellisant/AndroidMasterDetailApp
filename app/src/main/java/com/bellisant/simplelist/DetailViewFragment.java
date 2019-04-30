package com.bellisant.simplelist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailViewFragment extends Fragment {
    private static final String TAG = "DetailViewFragment";
    public static final String PARTNER_INDEX = "partnerIndex";

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

        View view = inflater.inflate(
                R.layout.fragment_detail_view,
                container,
                false);

        TextView nameTextView =
                view.findViewById(R.id.detail_fragment_name_text_view);
        TextView typeTextView =
                view.findViewById(R.id.detail_fragment_type_text_view);
        TextView descriptionTextView =
                view.findViewById(R.id.detail_fragment_description_text_view);

        int partnerIndex = getArguments().getInt(PARTNER_INDEX);

        Partner partner =
                ((MainActivity) getActivity()).getPartner(partnerIndex);

        nameTextView.setText(partner.getName());
        typeTextView.setText(partner.getType());
        descriptionTextView.setText(partner.getDescription());

        return view;
    }
}
