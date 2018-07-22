package com.degirmen.degirmenpersonalapplication.controller.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCopy {

    @SerializedName("ID")
    @Expose
    public String iD;
    @SerializedName("CategoryID")
    @Expose
    public String categoryID;
    @SerializedName("Count")
    @Expose
    public String count;
    @SerializedName("Price")
    @Expose
    public String price;
    @SerializedName("Comments")
    @Expose
    public String comments;

    @Override
    public String toString() {
        return "OrderCopy{" +
                "iD='" + iD + '\'' +
                ", categoryID='" + categoryID + '\'' +
                ", count='" + count + '\'' +
                ", price='" + price + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
