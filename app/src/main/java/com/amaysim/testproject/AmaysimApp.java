package com.amaysim.testproject;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by korn on 12/27/2016.
 */

public class AmaysimApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
