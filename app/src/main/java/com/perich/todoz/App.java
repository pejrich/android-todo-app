// getFileDirs() requires a instance Context, but I want to do read/write in a static method.

// This file creates a global Context for the app to allow access
// to the data files directory without passing around an instance "this" Context to static
// methods like List.save and List.read. See ln 28 of List.java (public static File getFileDir)

package com.perich.todoz;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}