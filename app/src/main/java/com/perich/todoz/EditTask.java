package com.perich.todoz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTask extends AppCompatActivity {

    EditText et;
    Button saveButton;
    Button deleteButton;

    String originalTaskText;
    Integer taskPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // initialize buttons/et variables
        et = (EditText) findViewById(R.id.etEditTask);
        saveButton = (Button) findViewById(R.id.saveButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        // Get data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            originalTaskText = extras.getString("taskText");
            taskPosition = extras.getInt("taskPosition");
        }
        // set EditText text
        et.setText(originalTaskText);
        // move cursor to end of the line
        et.setSelection(originalTaskText.length());
        // set up save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });
        // set up delete button click listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDelete();
            }
        });
    }

    public void onClickSave() {
        String newTaskText = et.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("newTaskText", newTaskText);
        intent.putExtra("taskPosition", taskPosition);
        setResult(1,intent);
        finish();
    }

    public void onClickDelete() {
        Intent intent = new Intent();
        intent.putExtra("taskPosition", taskPosition);
        setResult(2,intent);
        finish();
    }

}
