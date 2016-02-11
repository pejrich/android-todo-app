package com.perich.todoz;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class ListShow extends AppCompatActivity {

    public String listName;
    private ArrayList<Task> tasks;
    private TaskAdapter tasksAdapter;
    private ListView lvTasks;
    // Edit Activity requestCodes;
    private Integer initialRequestCode = 0;
    private Integer editResultCode = 1;
    private Integer deleteResultCode = 2;

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
        if (listData == null) {
            return;
        } else {
            listName = listData.getString("listName");
            setTitle(listName);
        }
        // init list view and item arrray list
        lvTasks = (ListView) findViewById(R.id.lvTasks);
        readTasks();
        if (tasks.isEmpty()) {
            Toast toast = Toast.makeText(this, R.string.no_tasks_message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 225);
            toast.show();
        }
        tasksAdapter = new TaskAdapter(this, R.layout.task_item, tasks);
        lvTasks.setAdapter(tasksAdapter);
        setupListViewListener();
    }


    private void setupListViewListener() {
        final Context that = this;
        lvTasks.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    toggleAtPosition(pos);
                }
            }
        );
        lvTasks.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                    // Start edit Intent
                    Intent intent = new Intent(that, EditTask.class);
                    intent.putExtra("taskText", tasks.get(pos).getName());
                    intent.putExtra("taskPosition", pos);
                    startActivityForResult(intent, initialRequestCode);
                    // Return true consumes the long click as handled
                    return true;
                }
            }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        Integer taskPosition = bundle.getInt("taskPosition");
        if (requestCode == initialRequestCode) {
            if (resultCode == editResultCode) {
                String newTaskText =  bundle.getString("newTaskText");
                tasks.get(taskPosition).setName(newTaskText);
                saveAndRefresh();
            } else if (resultCode == deleteResultCode) {
                deleteAtPosition(taskPosition);
            }
        }
    }

    public void deleteAtPosition(int position) {
        // Remove the item within array
        tasks.remove(position);
        saveAndRefresh();
    }

    public void toggleAtPosition(int position) {
        tasks.get(position).toggleStatus();
        saveAndRefresh();
    }

    public void onAddTask(View v) {
        EditText etNewTask = (EditText) findViewById(R.id.etNewTask);
        String taskText = etNewTask.getText().toString().trim();
        if (taskText.length() > 0) {
            Task newTask = new Task(taskText, 0);
            tasksAdapter.add(newTask);
            etNewTask.setText("");
            saveAndRefresh();
            Toast.makeText(this, R.string.task_name_success_flash, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.task_name_error_flash, Toast.LENGTH_SHORT).show();
        }

    }

    public void readTasks() {
        tasks = List.taskArray(listName);
    }

    public void writeTasks() {
        List.saveTasks(listName, tasks);
        // Show hint under list
        TextView t = (TextView) findViewById(R.id.underListNote);
        if (tasks.size() > 0) {
            t.setVisibility(View.VISIBLE);
        } else {
            t.setVisibility(View.INVISIBLE);
        }
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

    public void saveAndRefresh() {
        // Refresh the adapter
        tasksAdapter.notifyDataSetChanged();
        // write to files
        writeTasks();
    }
}
