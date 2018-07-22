package com.degirmen.degirmenpersonalapplication.controller.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCopy {

    @SerializedName("ID")
    @Expose
    public String id;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Parent")
    @Expose
    public String parent;
    @SerializedName("isproduct")
    @Expose
    public String isproduct;
    @SerializedName("Price")
    @Expose
    public String price;

    @Override
    public String toString() {
        return "ProductCopy{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", isproduct='" + isproduct + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}