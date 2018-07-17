package com.irfanulhaq.restaurantreservation.mvp.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.irfanulhaq.restaurantreservation.Utils.ConstantsUtils;
import com.irfanulhaq.restaurantreservation.FragmentHandler;
import com.irfanulhaq.restaurantreservation.OnBackPressListener;
import com.irfanulhaq.restaurantreservation.R;
import com.irfanulhaq.restaurantreservation.Utils.DataUtils;
import com.irfanulhaq.restaurantreservation.databinding.ActivityMainBinding;
import com.irfanulhaq.restaurantreservation.mvp.models.CustomerModel;
import com.irfanulhaq.restaurantreservation.mvp.presenters.MainActivityPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements
        CustomerFragment.ActivityInteraction, ActivityMVPView {
    private ActivityMainBinding mActivityBinding;
    private MainActivityPresenter presenter;
    private boolean[] tableStates;
    private OnBackPressListener onBackPressListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mActivityBinding.errorMessage.setVisibility(View.GONE);
        presenter = new MainActivityPresenter(this);
        presenter.attachView(this);
        presenter.loadCustomerData();
        presenter.loadTableData();
        DataUtils.SharedPrefDataUtils.setBooleanSharedPref(this,ConstantsUtils.MAIN_ACTIVITY_RUNNING,true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        DataUtils.SharedPrefDataUtils.setBooleanSharedPref(this,ConstantsUtils.MAIN_ACTIVITY_RUNNING,false);

    }

    @Override
    public void customerSelected(int position, CustomerModel customerModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantsUtils.CUSTOMER_KEY, customerModel);
        bundle.putBooleanArray(ConstantsUtils.TABLES_STATE_KEY, tableStates);
        TableFragment fragment = new TableFragment();
        fragment.setArguments(bundle);
        FragmentHandler.replaceFragment(fragment, getSupportFragmentManager(), mActivityBinding.fragmentContainer);

    }

    @Override
    public void onCustomerDataLoaded(List<CustomerModel> customerList) {
        mActivityBinding.splash.setVisibility(View.GONE);
        mActivityBinding.fragmentContainer.setVisibility(View.VISIBLE);
        CustomerFragment customerFragment = new CustomerFragment();
        onBackPressListener = customerFragment;
        Bundle bundle = new Bundle();
        bundle.putString("str", "data On the way");
        bundle.putParcelableArrayList(ConstantsUtils.CUSTOMERS_LIST_KEY, (ArrayList) customerList);
        customerFragment.setArguments(bundle);
        FragmentHandler.addFragment(customerFragment, getSupportFragmentManager(), mActivityBinding.fragmentContainer);

    }

    @Override
    public void onBackPressed() {
        if(onBackPressListener != null && ((CustomerFragment) onBackPressListener).isResumed()) {
            onBackPressListener.onBackPress(this);
        }
        super.onBackPressed();
    }

    @Override
    public void onTableDataLoaded(boolean[] tableStats) {
        this.tableStates = tableStats;
    }

    public void setOnBackPressListener(OnBackPressListener onBackPressListener){
        this.onBackPressListener = onBackPressListener;
    }

    @Override
    public void showError(Throwable e, boolean isInternetIssue) {
        mActivityBinding.progressBar.setVisibility(View.GONE);
        mActivityBinding.errorMessage.setVisibility(View.VISIBLE);
    }
}
