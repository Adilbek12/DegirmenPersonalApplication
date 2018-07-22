package com.degirmen.degirmenpersonalapplication.controller.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductOrderCopy {

    @SerializedName("ID")
    @Expose
    public String id;
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

    @SerializedName("Name")
    @Expose
    public String name;

    @Override
    public String toString() {
        return "ProductOrderCopy{" +
                "id='" + id + '\'' +
                ", categoryID='" + categoryID + '\'' +
                ", count='" + count + '\'' +
                ", price='" + price + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
