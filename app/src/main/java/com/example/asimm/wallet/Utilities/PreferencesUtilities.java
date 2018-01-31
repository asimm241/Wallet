package com.example.asimm.wallet.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by asimm on 11/21/2017.
 */

public class PreferencesUtilities {

    private static SharedPreferences mAppSharedPreferences = null;
    private static final String INCOME_PREFERENCES = "income";
    private static final String APP_PREFERENCES = "app_pref";

    private static Context mContext;

    public static void intit(Context context) {
        mContext = context;

        mAppSharedPreferences = context.getSharedPreferences(APP_PREFERENCES, 0);

    }

    public static void writeIncome(long income) {
        SharedPreferences.Editor editor = mAppSharedPreferences.edit();


        editor.putLong(INCOME_PREFERENCES, income);
        editor.apply();
    }

    public static long readIncome() {

        return mAppSharedPreferences.getLong(INCOME_PREFERENCES, 0);
    }

}
