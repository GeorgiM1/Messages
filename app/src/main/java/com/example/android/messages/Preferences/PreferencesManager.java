package com.example.android.messages.Preferences;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.messages.TimeInfo;
import com.google.gson.Gson;

/**
 * Created by pc on 4/8/2018.
 */

public class PreferencesManager {


        private static SharedPreferences getPreferences(Context context) {
            return context.getApplicationContext().getSharedPreferences("MySharedPreferencesFile", Activity.MODE_PRIVATE);
        }

    public static void addTimeInfo(TimeInfo timeInfo, Context c) {
        Gson gson = new Gson();
        String mapStrnig = gson.toJson(timeInfo);
        getPreferences(c).edit().putString("timeInfo", mapStrnig).apply();
    }

    public static TimeInfo getTimeInfo(Context context) {
        return new Gson().fromJson(getPreferences(context).getString("timeInfo", ""), TimeInfo.class);
    }

}
