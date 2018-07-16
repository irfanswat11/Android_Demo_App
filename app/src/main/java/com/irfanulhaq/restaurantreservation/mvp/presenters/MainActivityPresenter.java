package com.irfanulhaq.restaurantreservation.mvp.presenters;

import com.google.common.primitives.Booleans;
import com.google.common.primitives.Ints;
import com.irfanulhaq.restaurantreservation.Utils.ConstantsUtils;
import com.irfanulhaq.restaurantreservation.Utils.DataUtils;
import com.irfanulhaq.restaurantreservation.communication.ObserverManager;
import com.irfanulhaq.restaurantreservation.communication.ServiceApis;
import com.irfanulhaq.restaurantreservation.communication.Urls;
import com.irfanulhaq.restaurantreservation.mvp.models.CustomerModel;
import com.irfanulhaq.restaurantreservation.mvp.views.BaseActivity;
import com.irfanulhaq.restaurantreservation.mvp.views.MainActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter extends BaseMvpPresenter<MainActivity> {

    private MainActivityPresenter() {
    }

    public MainActivityPresenter(BaseActivity activity) {
        context = baseActivity = activity;
        observerManager = new ObserverManager();
        baseActivity.setPendingCallbacks(observerManager);
    }

    public void loadCustomerData() {
        Observable<List<CustomerModel>> observable = ServiceApis
                .getServiceApi().getCustomers(Urls.customerListUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        ObserverManager.ResponseObserver<List<CustomerModel>> observer = observerManager.new ResponseObserver<List<CustomerModel>>(baseActivity) {

            @Override
            public void onSuccess(List<CustomerModel> customerModels, boolean isFromServic) {
                getView().onCustomerDataLoaded(customerModels);
                if (isFromServic)
                    DataUtils.saveData(context, customerModels, ConstantsUtils.CUSTOMERS_LIST_KEY);
            }

            @Override
            public void onError(Throwable e, boolean isInternetIssue) {
                if (!isInternetIssue) {
                    getView().showError(e, isInternetIssue);
                } else {
                    try {
                        List<CustomerModel> list = DataUtils.getData(CustomerModel.class,context, ConstantsUtils.CUSTOMERS_LIST_KEY);
                        if (list != null && list.size() > 0) {
                            this.onSuccess(list, false);
                        } else {
                            getView().showError(new Throwable("Error:please check your internet and try again"), false);
                        }
                    }catch (IOException ex){
                        getView().showError(new Throwable("Error:"),false);
                    }

                }
            }
        };

        compstDsposbl.add(observable.subscribeWith(observer));
    }

    public void loadTableData() {
        Observable<boolean[]> observable = ServiceApis
                .getServiceApi().getTablesInfo(Urls.tablesUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        ObserverManager.ResponseObserver<boolean[]> observer = observerManager.new ResponseObserver<boolean[]>(baseActivity) {
            @Override
            public void onSuccess(boolean[] booleans, boolean isFromService) {
                getView().onTableDataLoaded(booleans);
                if (isFromService)
                    DataUtils.saveData(context, Booleans.asList(booleans),ConstantsUtils.TABLES_STATE_KEY);
            }

            @Override
            public void onError(Throwable e, boolean isInternetIssue) {
                if (!isInternetIssue) {
                    getView().showError(e, isInternetIssue);
                } else {
                    List<Boolean> list = null;
                    try {
                        list = DataUtils.getData(Boolean.class, context, ConstantsUtils.TABLES_STATE_KEY);
                        if(list != null && list.size() > 0){
                            boolean[] states =  Booleans.toArray(list);
                            this.onSuccess(states,false);
                        }else {
                            getView().showError(new Throwable("Error:please check your internet and try again"), false);
                        }
                    } catch (IOException ex) {
                        getView().showError(new Throwable("Error"),false);
                    }

                }
            }
        };
        compstDsposbl.add(observable.subscribeWith(observer));
    }
}
