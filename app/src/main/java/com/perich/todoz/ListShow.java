package com.perich.todoz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ListShow extends AppCompatActivity {

    private ArrayList<Task> tasks;
    private CustomAdapter tasksAdapter;
    private ListView lvTasks;
    private String dataFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_show);
        // Setup the Activitybar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Get data about list
        Bundle listData = getIntent().getExtras();
        String listName;
        if (listData == null) {
            return;
        } else {
            listName = listData.getString("listName");
            setTitle(listName);
        }
        // set data file name
        dataFileName = listName + "data.txt";
        // init list view and item arrray list
        lvTasks = (ListView) findViewById(R.id.lvTasks);
        readTasks();
        if (tasks.isEmpty()) {
            Toast toast = Toast.makeText(this, R.string.no_tasks_message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 225);
            toast.show();
        }
        tasksAdapter = new CustomAdapter(this, tasks);
        lvTasks.setAdapter(tasksAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvTasks.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    // Remove the item within array
                    tasks.get(pos).toggleStatus();
                    // Refresh the adapter
                    tasksAdapter.notifyDataSetChanged();
                    // write to files
                    writeTasks();
                }
            }
        );
        lvTasks.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                        // Remove the item within array
                        tasks.remove(pos);
                        // Refresh the adapter
                        tasksAdapter.notifyDataSetChanged();
                        // write to files
                    writeTasks();
                    // Return true consumes the long click as handled
                    return true;
                }
            }
        );
    }


    public void onAddTask(View v) {
        EditText etNewTask = (EditText) findViewById(R.id.etNewTask);
        String taskText = etNewTask.getText().toString().trim();
        if (taskText.length() > 0) {
            Task newTask = new Task(taskText, 0);
            tasksAdapter.add(newTask);
            etNewTask.setText("");
            writeTasks();
            Toast.makeText(this, R.string.task_name_success_flash, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.task_name_error_flash, Toast.LENGTH_SHORT).show();
        }

    }

    public void readTasks() {
        File filesDir = getFilesDir();
        File dataFile = new File(filesDir, dataFileName);
        try {
            ArrayList jsonTasks = new ArrayList<String>(FileUtils.readLines(dataFile));
            tasks = jsonToTasks(jsonTasks);
        } catch (IOException e) {
            tasks = new ArrayList<Task>();
        }
    }

    public void writeTasks() {
        File filesDir = getFilesDir();
        File dataFile = new File(filesDir, dataFileName);
        // Show hint under list
        TextView t = (TextView) findViewById(R.id.underListNote);
        if (tasks.size() > 0) {
            t.setVisibility(View.VISIBLE);
        } else {
            t.setVisibility(View.INVISIBLE);
        }
        // Write to file
        try{
            FileUtils.writeLines(dataFile, tasksToJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList tasksToJson() {
        ArrayList temp = new ArrayList<String>();
        for (Task task : tasks) {
            temp.add(task.toJSON().toString());
        }
        return temp;
    }

    private ArrayList jsonToTasks(ArrayList<String> jsonTasks) {
        ArrayList temp = new ArrayList<Task>();
        for (String task : jsonTasks) {
            try {
                JSONObject jsonTask = new JSONObject(task);
                String name = jsonTask.getString("name");
                Integer status = jsonTask.getInt("status");
                Task tempTask = new Task(name, status);
                temp.add(tempTask);
            } catch (Exception e) {}
        }
        return temp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_show_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
