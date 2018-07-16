package com.irfanulhaq.restaurantreservation.mvp.views;

import com.irfanulhaq.restaurantreservation.mvp.models.TableModel;

import java.util.List;

public interface TableMVPview extends BaseMvpView{
    void showTablzData(boolean[] tablz);
    void showError(Throwable e, boolean isInternetIssue);
    void refreshView();
}
