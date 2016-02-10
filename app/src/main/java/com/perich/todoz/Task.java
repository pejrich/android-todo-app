package com.perich.todoz;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Task {

    String name;  // Task name
    int status;  // completed (1) or not (0)


    Task(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return this.name;
    }
    public int getStatus() {
        return this.status;
    }
    public void toggleStatus() {
        this.status = (this.status == 0) ? 1 : 0;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", this.name);
            obj.put("status", this.status);
        } catch (JSONException e) {
            Log.e("showme", "unexpected JSON exception", e);
        }
        return obj;
    }
}
