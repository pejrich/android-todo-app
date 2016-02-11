package com.perich.todoz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
        // Grab display elements
        TextView name = (TextView) convertView.findViewById(R.id.listText);
        TextView stats = (TextView) convertView.findViewById(R.id.listStats);
        // Set list name
        String listName = listItems.get(position).getName();
        name.setText(listName);
        // Get info about number of tasks
        Integer numCompleted = List.numTasksCompleted(listName);
        Integer numTotal = List.numTasksTotal(listName);
        String statsString = numCompleted + "/" + numTotal;
        stats.setText(statsString);
        // If all completed, make it green and bold;
        if ((numCompleted == numTotal) && numTotal != 0) {
            name.setTextColor(Color.parseColor("#118B11"));
            stats.setTextColor(Color.parseColor("#118B11"));
            name.setTypeface(null, Typeface.BOLD);
            stats.setTypeface(null, Typeface.BOLD);
        }
        return convertView;
    }
}
