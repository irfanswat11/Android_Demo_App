package com.irfanulhaq.restaurantreservation.mvp.presenters;

import android.content.Context;

import com.irfanulhaq.restaurantreservation.communication.ObserverManager;
import com.irfanulhaq.restaurantreservation.mvp.views.BaseActivity;
import com.irfanulhaq.restaurantreservation.mvp.views.BaseFragment;
import com.irfanulhaq.restaurantreservation.mvp.views.BaseMvpView;

import io.reactivex.disposables.CompositeDisposable;


public class BaseMvpPresenter<T extends BaseMvpView> {
    protected T baseView;
    protected ObserverManager observerManager;
    protected BaseFragment baseFragment;
    protected BaseActivity baseActivity;
    protected CompositeDisposable compstDsposbl = new CompositeDisposable();
    protected Context context;


    public void attachView(T baseView) {
        this.baseView = baseView;
    }

    public void detachView() {
        this.baseView = null;
        if(!compstDsposbl.isDisposed()){
            compstDsposbl.dispose();
            compstDsposbl.clear();
        }
    }

    public T getView(){
        return baseView;
    }
    public boolean isViewAttached(){
            return baseView != null;
    }

    public void checkViewAttach() {
        if (!isViewAttached()) {
            throw new ViewNotAttachedException();
        }
    }

    public static class ViewNotAttachedException extends RuntimeException {
        public ViewNotAttachedException() {
            super("View Not Attached!");
        }
    }
}
