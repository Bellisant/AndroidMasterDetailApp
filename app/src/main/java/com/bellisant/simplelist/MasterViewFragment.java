package com.bellisant.simplelist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MasterViewFragment extends Fragment {
    private static final String TAG = "MasterViewFragment";

    private RecyclerView mRecyclerView;
    private RecyclerPartnerAdapter mRecyclerPartnerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // inflate root view
        View view = inflater.inflate(R.layout.fragment_master_view_recycler, container, false);

        // get RecyclerView
        mRecyclerView = view.findViewById(R.id.recycler_view);

        // set layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // create adapter for list
        List<Partner> partners = ((MainActivity) getActivity()).getPartners();
        mRecyclerPartnerAdapter = new RecyclerPartnerAdapter(partners);

        // set adapter to list
        mRecyclerView.setAdapter(mRecyclerPartnerAdapter);

        return view;
    }

    class RecyclerPartnerAdapter extends RecyclerView.Adapter<PartnerHolder> {
        private final List<Partner> mPartners;

        RecyclerPartnerAdapter(List<Partner> partners) {
            mPartners = partners;
        }

        @NonNull
        @Override
        public PartnerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.master_view_list_item, viewGroup, false);
            return new PartnerHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PartnerHolder partnerHolder, int position) {
            partnerHolder.bindTo(mPartners.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mPartners.size();
        }
    }

    class PartnerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private ImageView mLogo;
        private int position;

        PartnerHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextView = itemView.findViewById(R.id.name_view);
            mLogo = itemView.findViewById(R.id.logo_imageView);
        }

        void bindTo(Partner partner, int position) {
            this.position = position;

            Picasso.Builder builder = new Picasso.Builder(getContext());
            builder.downloader(new OkHttp3Downloader(getContext()));
            builder.build()
                    .load(partner.getImage())
//                    .networkPolicy(NetworkPolicy.NO_STORE)
                    .fit()
                    .into(mLogo);

            mTextView.setText(partner.getName());
        }

        @Override
        public void onClick(View v) {
            ((MainActivity) getActivity()).onPartnerItemClick(this.position);
        }
    }

    interface PartnerItemClicker {
        void onPartnerItemClick(int position);
    }
}
