package com.degirmen.degirmenpersonalapplication.controller.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCategoryCopy {

    @SerializedName("ID")
    @Expose
    public String id;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Parent")
    @Expose
    public String parent;
    @SerializedName("Printer")
    @Expose
    public String printer;

    @Override
    public String toString() {
        return "CategoryCopy{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", printer='" + printer + '\'' +
                '}';
    }
}
