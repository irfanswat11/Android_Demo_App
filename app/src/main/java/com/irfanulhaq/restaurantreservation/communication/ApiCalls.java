package com.irfanulhaq.restaurantreservation.communication;
import com.irfanulhaq.restaurantreservation.mvp.models.CustomerModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import io.reactivex.Observable;

public interface ApiCalls {
    @GET("{url}")
    public Observable<List<CustomerModel>> getCustomers(
            @Path(value = "url", encoded = true) String url);
    @GET("{url}")
    public Observable<boolean[]> getTablesInfo(
            @Path(value = "url", encoded = true) String url);
}
