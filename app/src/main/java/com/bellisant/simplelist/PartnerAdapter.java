package com.bellisant.simplelist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        TextView idView = listView.findViewById(R.id.id_view);
        TextView nameView = listView.findViewById(R.id.name_view);

        idView.setText(partner.getId());
        nameView.setText(partner.getName());

        return listView;
    }
}
