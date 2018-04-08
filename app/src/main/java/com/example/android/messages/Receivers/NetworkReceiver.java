package com.example.android.messages.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.android.messages.Service.BackgroundService;

/**
 * Created by pc on 4/8/2018.
 */

public class NetworkReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, BackgroundService.class);
        context.startService(background);
        Toast.makeText(context, "SMS sent", Toast.LENGTH_SHORT).show();


    }
}
