package com.example.asimm.wallet.App;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.asimm.wallet.Utilities.PreferencesUtilities;
import com.example.asimm.wallet.database.AppDatabase;

/**
 * Created by asimm on 11/21/2017.
 */

public class App extends Application {

    private static Context applicationContext;

    private static App instance = null;
    private static AppDatabase appDatabase;
    private static final String DatabaseName = "wallet.db";


    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    public static synchronized App getInstance() {
        return instance;
    }

    private void init() {
        App.instance = this;
        applicationContext = getApplicationContext();
        PreferencesUtilities.intit(applicationContext);
        getAppDatabase();
    }

    public static AppDatabase getAppDatabase() {
        if (appDatabase == null) {
            App.appDatabase = Room.databaseBuilder(App.getInstance().getApplicationContext(), AppDatabase.class, App.DatabaseName)
                    .build();
        }
        return appDatabase;
    }
}
