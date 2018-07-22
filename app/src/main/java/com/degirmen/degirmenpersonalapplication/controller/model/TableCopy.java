package com.degirmen.degirmenpersonalapplication.controller.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableCopy {

  @SerializedName("Name")
  @Expose
  public String name;
  @SerializedName("TableID")
  @Expose
  public String tableID;
  @SerializedName("UserID")
  @Expose
  public String userID;

}
