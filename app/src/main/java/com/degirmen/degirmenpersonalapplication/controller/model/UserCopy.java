package com.degirmen.degirmenpersonalapplication.controller.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserJson {

    @SerializedName("ID")
    @Expose
    public String iD;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Password")
    @Expose
    public String password;

    public UserJson(){

    }

    @Override
    public String toString() {
        return "UserJson{" +
                "iD='" + iD + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}