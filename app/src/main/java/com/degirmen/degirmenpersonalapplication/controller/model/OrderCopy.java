package com.degirmen.degirmenpersonalapplication.controller.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrderCopy {

    @SerializedName("ID")
    @Expose
    public String iD;
    @SerializedName("StartTime")
    @Expose
    public String startTime;
    @SerializedName("UserID")
    @Expose
    public String userID;
    @SerializedName("Counter")
    @Expose
    public String counter;
    @SerializedName("TableID")
    @Expose
    public String tableID;
    @SerializedName("TotalPrice")
    @Expose
    public String totalPrice;
    @SerializedName("Tarif")
    @Expose
    public String tarif;

    @Override
    public String toString() {
        return "MyOrderCopy{" +
                "id='" + iD + '\'' +
                ", startTime='" + startTime + '\'' +
                ", userID='" + userID + '\'' +
                ", counter='" + counter + '\'' +
                ", tableID='" + tableID + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", tarif='" + tarif + '\'' +
                '}';
    }
}
