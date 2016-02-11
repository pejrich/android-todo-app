// This file creates a global Context for the all to allow access
// of the data files directory without passing around an instance "this" Context to statis
// methods like List.save and List.read

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