package com.degirmen.degirmenpersonalapplication.controller.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryCopy {

    @SerializedName("ID")
    @Expose
    public String iD;
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
                "id='" + iD + '\'' +
                ", name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", printer='" + printer + '\'' +
                '}';
    }
}
