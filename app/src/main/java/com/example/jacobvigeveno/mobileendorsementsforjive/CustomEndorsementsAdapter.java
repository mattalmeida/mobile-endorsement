package com.example.jacobvigeveno.mobileendorsementsforjive;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomEndorsementsAdapter extends ArrayAdapter<String> {
    private ListView listView;
    public CustomEndorsementsAdapter(Context context, ArrayList<String> endorsements, ListView listView) {
        super(context, 0, endorsements);
        this.listView = listView;
    }

    static class ViewHolder {
        TextView text;
        Button btn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String endorsement = getItem(position); // populate

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_view_row, parent, false);
            ViewHolder h = new ViewHolder();
            h.text = (TextView) rowView.findViewById(R.id.item_text);
            h.btn = rowView.findViewById(R.id.btn);
            rowView.setTag(h);
        }

        ViewHolder h = (ViewHolder) rowView.getTag();

        h.indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DO what you want to recieve on btn click there.
            }
        });


        return rowView;
    }
}

