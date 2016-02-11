package com.perich.todoz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter {

    ArrayList<List> listItems = new ArrayList<>();
    Context context;

    public ListAdapter(Context context, int layout, ArrayList resource) {
        super(context, layout, resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.listItems = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.list_item, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.listText);
        TextView stats = (TextView) convertView.findViewById(R.id.listStats);
        String listName = listItems.get(position).getName();
        name.setText(listName);
        stats.setText(List.getStats(listName));
        return convertView;
    }
}
