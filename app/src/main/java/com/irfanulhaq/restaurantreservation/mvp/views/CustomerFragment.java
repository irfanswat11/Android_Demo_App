package com.irfanulhaq.restaurantreservation.mvp.views;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.irfanulhaq.restaurantreservation.Utils.ConstantsUtils;
import com.irfanulhaq.restaurantreservation.CustomRecyclVwrAdapter;
import com.irfanulhaq.restaurantreservation.OnBackPressListener;
import com.irfanulhaq.restaurantreservation.R;
import com.irfanulhaq.restaurantreservation.databinding.FragmentCustomerBinding;
import com.irfanulhaq.restaurantreservation.databinding.RecyclerViewRowBinding;
import com.irfanulhaq.restaurantreservation.mvp.models.CustomerModel;
import com.irfanulhaq.restaurantreservation.mvp.presenters.CustomerPresenter;

import java.util.ArrayList;
import java.util.List;

public class CustomerFragment extends BaseFragment implements CustomerMVPView, OnBackPressListener {
    FragmentCustomerBinding layoutBinding;
    CustomerPresenter customerPresenter;
    ActivityInteraction callbacks;
    RcyclrVwAdptr rcyclrVwAdptr;
    List<CustomerModel> customerModelList;


    public interface ActivityInteraction {
        void customerSelected(int positon, CustomerModel customerModel);
    }

    CustomRecyclVwrAdapter.OntItemClickListener ontItemClickListener = new CustomRecyclVwrAdapter.OntItemClickListener<CustomerModel>() {
        @Override
        public void onItemClicked(CustomerModel customerModel, int position) {
            Toast.makeText(getContext(), "Name: " + customerModel.customerFirstName, Toast.LENGTH_LONG).show();
            callbacks.customerSelected(position, customerModel);
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerModelList = getArguments().getParcelableArrayList(ConstantsUtils.CUSTOMERS_LIST_KEY);
    }

    @Override
    public void onBackPress(BaseActivity baseActivity) {
            baseActivity.finish();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callbacks = (ActivityInteraction) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutBinding = FragmentCustomerBinding.bind(view);
        layoutBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        layoutBinding.recyclerView.setHasFixedSize(true);
        rcyclrVwAdptr = new RcyclrVwAdptr(customerModelList, ontItemClickListener);
        layoutBinding.recyclerView.setAdapter(rcyclrVwAdptr);
        layoutBinding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rcyclrVwAdptr.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void showCustomerData(List<CustomerModel> customerData) {
        rcyclrVwAdptr = new RcyclrVwAdptr(customerData, ontItemClickListener);
        layoutBinding.recyclerView.setAdapter(rcyclrVwAdptr);
    }

    @Override
    public void showError(boolean isInternetIssue) {
    }

    private class RcyclrVwAdptr extends CustomRecyclVwrAdapter<RcyclrVwAdptr.ViewHolder> implements Filterable {
        List<CustomerModel> filteredList = new ArrayList<>();
        List<CustomerModel> customerModelList = new ArrayList<>();

        OntItemClickListener onItemClickListener;

        public RcyclrVwAdptr(List<CustomerModel> customerModels, OntItemClickListener ontItemClickListener) {
            this.filteredList.addAll(customerModels);
            this.customerModelList.addAll(customerModels);
            this.onItemClickListener = ontItemClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.recycler_view_row, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.rowBinding.customerName
                    .setText(filteredList.get(position).customerFirstName + " " + filteredList.get(position).customerLastName);
        }

        @Override
        public int getItemCount() {
            return filteredList.size();
        }

        @Override
        public Filter getFilter() {

            return new Filter() {

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    filteredList.clear();
                    if (constraint.length() == 0) {
                        filteredList.addAll(customerModelList);
                    } else {
                        for (CustomerModel customerModel : customerModelList) {
                            if (customerModel.customerFirstName.toLowerCase().contains(constraint.toString().toLowerCase()))
                                filteredList.add(customerModel);
                        }
                    }
                    FilterResults results = new FilterResults();
                    results.values = filteredList;
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    notifyDataSetChanged();
                }
            };
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public RecyclerViewRowBinding rowBinding;

            public ViewHolder(View view) {
                super(view);
                rowBinding = RecyclerViewRowBinding.bind(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        onItemClickListener.onItemClicked(filteredList.get(position), position);
                    }
                });
            }
        }


    }
}
