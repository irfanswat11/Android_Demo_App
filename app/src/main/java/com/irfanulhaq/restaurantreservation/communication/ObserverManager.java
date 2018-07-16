package com.irfanulhaq.restaurantreservation.communication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;

public class ObserverManager {
    SparseArray<ResponseObserver.PendingCallsHandler> pendingCalls = new SparseArray<>();

    public int totalPendingCalls() {
        return pendingCalls.size();
    }

    public void executePendingCalls() {
        if (totalPendingCalls() > 0) {
            for (int i = 0; i < pendingCalls.size(); i++) {
                pendingCalls.get(pendingCalls.keyAt(i)).run();
            }
            clearPendingCalls();
        }

    }

    public void clearPendingCalls() {
        pendingCalls.clear();
    }


    public abstract class ResponseObserver<T> extends DisposableObserver<T> {
        public abstract void onSuccess(T t,boolean isFromService);

        public abstract void onError(Throwable e, boolean isInternetIssue);

        Fragment fragment;
        AppCompatActivity activity;
        Activity a;

        private ResponseObserver() {
        }

        //for fragment;
        public ResponseObserver(Fragment fragment) {
            this.fragment = fragment;
        }

        //for activity
        public ResponseObserver(AppCompatActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onNext(T t) {
            if((fragment != null && fragment.isResumed() && fragment.isAdded()) ||
                    (activity != null && !activity.isFinishing())){
                onSuccess(t,true);
            }else {
                pendingCalls.append(this.hashCode(),new PendingCallsHandler(t,false,null));
            }

        }

        @Override
        public void onError(Throwable e) {
            boolean isInternetIssue = e instanceof UnknownHostException ||
                    e instanceof TimeoutException || e instanceof IOException;
            if((fragment != null && fragment.isResumed() && fragment.isAdded()) ||
                    (activity != null && !activity.isFinishing())){

                onError(e, isInternetIssue);
            }else {
                pendingCalls.append(this.hashCode(),new PendingCallsHandler(null,isInternetIssue,e));
            }

        }

        @Override
        public void onComplete() {

        }

        class PendingCallsHandler implements Runnable {
            private T t;
            private boolean isInternetIssue;
            private Throwable e;

            public PendingCallsHandler(T t, boolean isInternetIssue, Throwable e) {
                this.t = t;
                this.isInternetIssue = isInternetIssue;
                this.e = e;
            }

            @Override
            public void run() {
                if (e == null) {
                    onSuccess(t,true);

                } else {
                    onError(e, isInternetIssue);
                }
            }
        }
    }


}
