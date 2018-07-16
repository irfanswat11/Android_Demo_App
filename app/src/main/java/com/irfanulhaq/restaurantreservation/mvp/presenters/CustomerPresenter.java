package com.irfanulhaq.restaurantreservation.mvp.presenters;

import com.irfanulhaq.restaurantreservation.communication.ObserverManager;
import com.irfanulhaq.restaurantreservation.communication.ServiceApis;
import com.irfanulhaq.restaurantreservation.communication.Urls;
import com.irfanulhaq.restaurantreservation.mvp.models.CustomerModel;
import com.irfanulhaq.restaurantreservation.mvp.views.BaseFragment;
import com.irfanulhaq.restaurantreservation.mvp.views.CustomerMVPView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CustomerPresenter extends BaseMvpPresenter<CustomerMVPView> {

    private CustomerPresenter(){}

    public CustomerPresenter(BaseFragment baseFragment){
        this.context = (this.baseFragment = baseFragment).getActivity();
        observerManager = new ObserverManager();
        baseFragment.setPendingCallback(observerManager);
    }




}
