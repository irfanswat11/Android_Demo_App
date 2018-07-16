package com.irfanulhaq.restaurantreservation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.irfanulhaq.restaurantreservation.mvp.models.CustomerModel;

public abstract class CustomRecyclVwrAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    public interface OntItemClickListener<V>{
        public void onItemClicked(V dataModel,int position);
    }
}
