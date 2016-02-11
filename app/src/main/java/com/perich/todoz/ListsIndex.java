package com.perich.todoz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListsIndex extends AppCompatActivity {

    private ArrayList<List> lists;
    private ListAdapter listsAdapter;
    private ListView lvLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_index);
        // get list view
        lvLists = (ListView) findViewById(R.id.lvLists);
        // Load lists from file (if any)
        readLists();
        // if no lists, display a message.
        if (lists.isEmpty()) {
            Toast toast = Toast.makeText(this, R.string.no_lists_message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 225);
            toast.show();
        }
        // Set up adapter
        listsAdapter = new ListAdapter(this,
                R.layout.list_item, lists);
        lvLists.setAdapter(listsAdapter);
        // Set up long click delete listener
        setupListViewListeners();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        // This reloads the task count stats
        listsAdapter.notifyDataSetChanged();
    }

    public void onAddList(View v) {
        EditText etNewList = (EditText) findViewById(R.id.etNewList);
        String listText = etNewList.getText().toString().trim();
        if (listText.length() > 0) {
            List newList = new List(listText);
            listsAdapter.add(newList);
            etNewList.setText("");
            saveAndRefresh();
            Toast.makeText(this, R.string.list_name_success_flash, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.list_name_error_flash, Toast.LENGTH_SHORT).show();
        }

    }

    private void readLists() {
        lists = List.all();
    }

    private void writeLists() {
        List.saveAll(lists);
        // Show hint under list
        TextView t = (TextView) findViewById(R.id.underListNote);
        if (lists.size() > 0) {
            t.setVisibility(View.VISIBLE);
        } else {
            t.setVisibility(View.INVISIBLE);
        }
    }

    private void setupListViewListeners() {
        // tap to select
        final Context context = this;
        lvLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
                String clickedText = lists.get(pos).name;
                Intent i = new Intent(context, ListShow.class);
                i.putExtra("listName", clickedText);
                startActivity(i);
            }
        });

        // Long click to delete
        lvLists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> p, View v, int pos, long id) {
                // delete task list data file
                List.deleteList(lists.get(pos).getName());
                // Remove the item within array
                lists.remove(pos);
                saveAndRefresh();
                // Return true consumes the long click as handled
                return true;
            }
        });
    }

    public void saveAndRefresh() {
        // Refresh the adapter
        listsAdapter.notifyDataSetChanged();
        // write to files
        writeLists();
    }
}
