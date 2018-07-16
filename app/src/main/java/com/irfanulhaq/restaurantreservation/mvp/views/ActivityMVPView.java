package com.irfanulhaq.restaurantreservation.mvp.views;

import com.irfanulhaq.restaurantreservation.mvp.models.BaseMvpPModel;
import com.irfanulhaq.restaurantreservation.mvp.models.CustomerModel;

import java.util.List;

public interface ActivityMVPView extends BaseMvpView {
    void onCustomerDataLoaded(List<CustomerModel> customerList);
    void onTableDataLoaded(boolean[] tableStats);

    void showError(Throwable e, boolean isInternetIssue);
}
