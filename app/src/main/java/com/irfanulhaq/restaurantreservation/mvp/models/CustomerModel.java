package com.irfanulhaq.restaurantreservation.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CustomerModel extends BaseMvpPModel implements Parcelable{
    @Expose
    @SerializedName("id")
    public int id;
    @Expose
    @SerializedName("customerFirstName")
    public String customerFirstName;
    @Expose
    @SerializedName("customerLastName")
    public String customerLastName;

    public CustomerModel() {
    }


    public CustomerModel(int id,String customerFirstName,String customerLastName) {
        this.id = id;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;

    }


    public static final Parcelable.Creator<CustomerModel> CREATOR =
            new Creator<CustomerModel>() {
                @Override
                public CustomerModel createFromParcel(Parcel source) {
                    return new CustomerModel(source);
                }

                @Override
                public CustomerModel[] newArray(int size) {
                    return new CustomerModel[0];
                }
            };

    private CustomerModel(Parcel parcel){
        this.id = parcel.readInt();
        this.customerFirstName = parcel.readString();
        this.customerLastName = parcel.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.customerFirstName);
        dest.writeString(this.customerLastName);
    }

}
