package com.degirmen.degirmenpersonalapplication.controller.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewTableCopy {

  @SerializedName("ID")
  @Expose
  public String id;
  @SerializedName("Counter")
  @Expose
  public String counter;

  @Override
  public String toString() {
    return "NewTableCopy{" +
      "id='" + id + '\'' +
      ", counter='" + counter + '\'' +
      '}';
  }
}
