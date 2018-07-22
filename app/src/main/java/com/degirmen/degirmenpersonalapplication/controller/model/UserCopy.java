package com.degirmen.degirmenpersonalapplication.controller.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCopy {

    @SerializedName("ID")
    @Expose
    public String id;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Password")
    @Expose
    public String password;

    public UserCopy(){

    }

    @Override
    public String toString() {
        return "UserJson{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}