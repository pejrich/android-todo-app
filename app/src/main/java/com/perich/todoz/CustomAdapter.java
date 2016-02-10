package com.perich.todoz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Task> {

    ArrayList<Task> modelItems = new ArrayList<Task>();
    Context context;

    public CustomAdapter(Context context, ArrayList<Task> resource) {
        super(context, R.layout.task_item, resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.modelItems = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.task_item, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.taskText);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.taskCheckbox);
        name.setText(modelItems.get(position).getName());
        if (modelItems.get(position).getStatus() == 1) {
            cb.setChecked(true);
        } else {
            cb.setChecked(false);
        }
        return convertView;
    }
}

