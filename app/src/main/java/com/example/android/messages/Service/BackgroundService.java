package com.example.android.messages.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.android.messages.Preferences.PreferencesManager;
import com.example.android.messages.Receivers.NetworkReceiver;
import com.example.android.messages.TimeInfo;

import java.util.Calendar;

/**
 * Created by pc on 4/8/2018.
 */

public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    private PendingIntent pendingIntent;
    private TimeInfo timeInfo;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
        timeInfo = PreferencesManager.getTimeInfo(BackgroundService.this);

    }

    private Runnable myTask = new Runnable() {
        public void run() {
            Intent alarmIntent =  new Intent(BackgroundService.this, NetworkReceiver.class);
            pendingIntent = PendingIntent.getActivity(BackgroundService.this,0,alarmIntent,0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(BackgroundService.this.ALARM_SERVICE);
            Calendar cal = Calendar.getInstance();
            cal.set(timeInfo.getYear(),timeInfo.getMonth(),timeInfo.getDay(),timeInfo.getMinute(),timeInfo.getHour());
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    cal.getTimeInMillis(), pendingIntent);
            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
     return START_STICKY;
    }

}

