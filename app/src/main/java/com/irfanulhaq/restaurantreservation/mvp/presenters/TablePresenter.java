package com.irfanulhaq.restaurantreservation.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.irfanulhaq.restaurantreservation.Utils.ConstantsUtils;
import com.irfanulhaq.restaurantreservation.Utils.DataUtils;
import com.irfanulhaq.restaurantreservation.communication.ObserverManager;
import com.irfanulhaq.restaurantreservation.communication.ServiceApis;
import com.irfanulhaq.restaurantreservation.communication.Urls;
import com.irfanulhaq.restaurantreservation.mvp.models.TableModel;
import com.irfanulhaq.restaurantreservation.mvp.views.BaseFragment;
import com.irfanulhaq.restaurantreservation.mvp.views.TableMVPview;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TablePresenter extends BaseMvpPresenter<TableMVPview> {
    Timer timer;


    public TablePresenter(BaseFragment baseFragment) {
        this.context = (this.baseFragment = baseFragment).getActivity();
        observerManager = new ObserverManager();
        baseFragment.setPendingCallback(observerManager);
    }
    public TablePresenter(Context context){
        this.context = context;
    }

    public void reserveTable(HashMap<Integer,Integer> bookedTables) {
        try {
            HashMap<Integer,Integer> oldReservation = DataUtils.getData(Integer.class,Integer.class,context,ConstantsUtils.TABLS_BOOKING_KEY);
            if(oldReservation != null){
                oldReservation.putAll(bookedTables);
                DataUtils.saveData(context,oldReservation,ConstantsUtils.TABLS_BOOKING_KEY);
            }else {
                DataUtils.saveData(context,bookedTables,ConstantsUtils.TABLS_BOOKING_KEY);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
