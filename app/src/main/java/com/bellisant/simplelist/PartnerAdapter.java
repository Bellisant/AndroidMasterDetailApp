package com.bellisant.simplelist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PartnerAdapter extends ArrayAdapter<Partner> {

    private static final String TAG = "PartnerAdapter";

    public PartnerAdapter(Context context, List<Partner> partners) {
        super(context, 0, partners);
    }

    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {

        // check if specified view was not reused then inflate it
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext())
                    .inflate(
                            R.layout.master_view_list_item,
                            parent,
                            false
                    );
        }

        Log.v(TAG, "getView(): view for item " + position + "provided");

        Partner partner = getItem(position);
        TextView nameView = listView.findViewById(R.id.name_view);
        ImageView logoImageView = listView.findViewById(R.id.logo_imageView);

        setNameTextView(partner, nameView);
        setLogoImageView(partner, logoImageView);

        return listView;
    }

    private void setNameTextView(Partner partner, TextView nameView) {
        nameView.setText(partner.getName());
    }

    private void setLogoImageView(Partner partner, ImageView iv) {
        final ImageView logoImageView = iv;
        if (partner.getImage() != null) {

            String msg =
                    String.format("setLogoImageView(): loading image from %s" +
                                    "for %s",
                            partner.getImage(),
                            partner.getName());
            Log.v(TAG, msg);

            Picasso.get()
                    .load(partner.getImage())
                    .fit()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(logoImageView
                            ,
                            new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    logoImageView.setVisibility(View.GONE);
                                }
                            }
                            );
        } else {
            String msg =
                    String.format("setLogoImageView(): no image for %s",
                            partner.getName());
            Log.v(TAG, msg);

            logoImageView.setVisibility(View.GONE);
        }
    }
}
