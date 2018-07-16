package com.irfanulhaq.restaurantreservation;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

public class FragmentHandler {

    public static void addFragment(Fragment fragment, FragmentManager fragmentManager, FrameLayout container){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(container.getId(),fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
    public static void replaceFragment(Fragment fragment,FragmentManager fragmentManager, FrameLayout container){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(container.getId(),fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
    public static Fragment getTopFragment(FragmentManager fragmentManager){
        int backStackSiz = fragmentManager.getBackStackEntryCount();
        return fragmentManager.getFragments().get(backStackSiz > 0 ? backStackSiz-1: backStackSiz);
    }
}
