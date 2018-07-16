package com.irfanulhaq.restaurantreservation.mvp.views;

import android.support.v4.app.Fragment;

import com.irfanulhaq.restaurantreservation.communication.ObserverManager;

public class BaseFragment extends Fragment {

    ObserverManager observerManager;
    interface OnBackPressedListener{
    }


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

    public void setPendingCallback(ObserverManager observerManager){
        this.observerManager = observerManager;
    }
}
