package com.irfanulhaq.restaurantreservation;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.irfanulhaq.restaurantreservation.Utils.ConstantsUtils;
import com.irfanulhaq.restaurantreservation.Utils.DataUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SchedulingService extends Service {

    Timer timer = new Timer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                HashMap<Integer, Integer> bookedTables = null;
                DataUtils.saveData(getApplicationContext(), bookedTables, ConstantsUtils.TABLS_BOOKING_KEY);
                if (DataUtils.SharedPrefDataUtils.getBooleanSharedPref(getApplicationContext(), ConstantsUtils.MAIN_ACTIVITY_RUNNING)) {
                    Intent intent = new Intent(ConstantsUtils.TABLE_FRAG_BROADCAST);
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                } else {
                    stopSelf();
                }
            }
        }, ConstantsUtils.RESET_INTERVAL, ConstantsUtils.RESET_INTERVAL);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer.purge();
    }
}
