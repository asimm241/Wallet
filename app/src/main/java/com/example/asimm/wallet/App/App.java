package com.example.asimm.wallet.App;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.asimm.wallet.Utilities.PreferencesUtilities;

/**
 * Created by asimm on 11/21/2017.
 */

public class App extends Application {

    private static Context applicationContext;
    private static App instance = null;


    @Override
    public void onCreate() {
        super.onCreate();
        App.instance = this;
        applicationContext = getApplicationContext();
        PreferencesUtilities.intit(applicationContext);

    }
}
