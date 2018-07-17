package com.irfanulhaq.restaurantreservation.mvp.views;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.irfanulhaq.restaurantreservation.SchedulingService;
import com.irfanulhaq.restaurantreservation.Utils.ConstantsUtils;
import com.irfanulhaq.restaurantreservation.R;
import com.irfanulhaq.restaurantreservation.databinding.FragmentTableBinding;
import com.irfanulhaq.restaurantreservation.databinding.GridCellBinding;
import com.irfanulhaq.restaurantreservation.mvp.models.CustomerModel;
import com.irfanulhaq.restaurantreservation.mvp.presenters.TablePresenter;

import java.util.Arrays;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends BaseFragment implements TableMVPview {

    FragmentTableBinding layoutBinding;


    TablePresenter tablePresenter;//presenter;
    CustomerModel customerModel;
    GridAdapter gridAdapter;
    boolean[] tablesStates;
    HashMap<Integer,Integer> reservedTabls = new HashMap<Integer, Integer>();

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshView();
        }
    };

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            boolean isReserved = (boolean) layoutBinding.tabzGrid.getAdapter().getItem(position);
            if (!isReserved) {
                view.setBackgroundColor(Color.GRAY);
                reservedTabls.put(position,customerModel.id);
                gridAdapter.setItem(position, true);
            } else {
                Toast.makeText(getContext(), "Table " + (position + 1) + " is Reserved", Toast.LENGTH_LONG).show();
            }
        }
    };
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
               case  R.id.booking_tabl:
                   tablePresenter.reserveTable(reservedTabls);
                   reservedTabls.clear();
                   gridAdapter.notifyDataSetChanged();
                break;
                case  R.id.cancel_booking:
                    if(reservedTabls.keySet().size() > 0) {
                        for (int i : reservedTabls.keySet()) {
                            gridAdapter.setItem(i, false);
                        }
                        reservedTabls.clear();
                        gridAdapter.notifyDataSetChanged();
                    }
                    break;
            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tablePresenter = new TablePresenter(this);
        tablePresenter.attachView(this);
        tablesStates = getArguments().getBooleanArray(ConstantsUtils.TABLES_STATE_KEY);
        customerModel = getArguments().getParcelable(ConstantsUtils.CUSTOMER_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter(ConstantsUtils.TABLE_FRAG_BROADCAST));
        layoutBinding = FragmentTableBinding.bind(view);
        gridAdapter = new GridAdapter(tablesStates, getLayoutInflater(), getContext());
        layoutBinding.tabzGrid.setAdapter(gridAdapter);
        layoutBinding.tabzGrid.setOnItemClickListener(itemClickListener);
        layoutBinding.reservTabl.setOnClickListener(onClickListener);
        layoutBinding.bookingTabl.setOnClickListener(onClickListener);
        layoutBinding.cancelBooking.setOnClickListener(onClickListener);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter(ConstantsUtils.TABLE_FRAG_BROADCAST));
        getContext().startService(new Intent(getContext(), SchedulingService.class));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //start intent service;
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void showTablzData(boolean[] tablz) {
        layoutBinding.tabzGrid.setAdapter(new GridAdapter(tablz, getLayoutInflater(), getContext()));
    }

    @Override
    public void showError(Throwable e, boolean isInternetIssue) {

    }

    @Override
    public void refreshView() {
        gridAdapter.resetAllValues();
    }


    private class GridAdapter extends BaseAdapter {
        boolean[] tablz;
        LayoutInflater mlayoutInflator;
        Context context;
        GridCellBinding gridCellBinding;

        public GridAdapter(boolean[] tablz, LayoutInflater mlayoutInflator, Context context) {
            this.context = context;
            this.tablz = tablz;
            this.mlayoutInflator = mlayoutInflator;
        }

        public void setItem(int position, boolean b) {
            tablz[position] = b;
        }

        public void resetAllValues() {
            Arrays.fill(tablz, false);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return tablz.length;
        }

        @Override
        public Object getItem(int position) {
            return tablz[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mlayoutInflator.inflate(R.layout.grid_cell, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            convertView.setBackgroundColor(Color.GREEN);
            viewHolder.item_text.setText(tablz[position] ? (position + 1) + ":R" : (position + 1) + ":A");
            viewHolder.item_text.setTextColor(tablz[position] ? Color.RED : Color.BLACK);
            viewHolder.item_text.setEnabled(!tablz[position]);
            return convertView;
        }

        public class ViewHolder {
            public TextView item_text;

            public ViewHolder(View convertView) {
                item_text = (TextView) convertView.findViewById(R.id.table_name);
            }
        }
    }
}
