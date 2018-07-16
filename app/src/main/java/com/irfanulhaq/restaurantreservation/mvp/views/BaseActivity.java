package com.irfanulhaq.restaurantreservation.mvp.views;

import android.support.v7.app.AppCompatActivity;

import com.irfanulhaq.restaurantreservation.communication.ObserverManager;

public class BaseActivity extends AppCompatActivity {
    ObserverManager observerManager;
    @Override
    public void onResume() {
        super.onResume();
        if(observerManager != null){
            observerManager.executePendingCalls();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void setPendingCallbacks(ObserverManager observerManager){
        this.observerManager = observerManager;
    }
}
