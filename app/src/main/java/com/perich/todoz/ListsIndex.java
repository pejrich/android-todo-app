package com.perich.todoz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListsIndex extends AppCompatActivity {

    private static final String fileFileName = "lists.txt";

    private ArrayList<String> lists;
    private ArrayAdapter<String> listsAdapter;
    private ListView lvLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_index);
        // get list view
        lvLists = (ListView) findViewById(R.id.lvLists);
        // initialize lists ArrayList
        lists = new ArrayList<String>();
        // Load lists from file (if any)
        readLists();
        // if no lists, display a message.
        if (lists.isEmpty()) {
            Toast toast = Toast.makeText(this, "Add a list", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 225);
            toast.show();
        }
        // Set up adapter
        listsAdapter = new ArrayAdapter<String>(this,
                R.layout.list_item, lists);
        lvLists.setAdapter(listsAdapter);
        // Set up long click delete listener
        setupListViewListeners();
    }

    public void onAddList(View v) {
        EditText etNewList = (EditText) findViewById(R.id.etNewList);
        String listText = etNewList.getText().toString();
        listsAdapter.add(listText);
        etNewList.setText("");
        writeLists();
        Toast.makeText(this, "List added", Toast.LENGTH_SHORT).show();
    }

    private void readLists() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, fileFileName);
        try {
            lists = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            lists = new ArrayList<String>();
        }
    }

    private void writeLists() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, fileFileName);
        try{
            FileUtils.writeLines(todoFile, lists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupListViewListeners() {
        // tap to select
        final Context context = this;
        lvLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
                String clickedText = "clicked on " + lists.get(pos);
                Intent i = new Intent(context, ListShow.class);
                i.putExtra("listName", clickedText);
                startActivity(i);
            }
        });

        // Long click to delete
        lvLists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> p, View v, int pos, long id) {
                // Remove the item within array
                lists.remove(pos);
                // Refresh the adapter
                listsAdapter.notifyDataSetChanged();
                // write to files
                writeLists();
                // Return true consumes the long click as handled
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lists_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
