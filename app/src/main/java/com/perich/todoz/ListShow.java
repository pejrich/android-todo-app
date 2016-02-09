package com.perich.todoz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ListShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_show);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.listShowBar);
//        setSupportActionBar(toolbar);

        Bundle listData = getIntent().getExtras();

        if (listData == null) {
            return;
        } else {
            String listName = listData.getString("listName");
            Log.i("showme", "On list show with" + listName);
            TextView listShowText = (TextView) findViewById(R.id.listShowText);
            listShowText.setText(listName);
        }
    }

    public void onBackButtonClick(View v) {
        finish();
    }

}
