package com.irfanulhaq.restaurantreservation.mvp.views;

import com.irfanulhaq.restaurantreservation.mvp.models.CustomerModel;

import java.util.List;

public interface CustomerMVPView extends BaseMvpView {
     void showCustomerData(List<CustomerModel> customerData);
     void showError(boolean isInternetIssue);
}
