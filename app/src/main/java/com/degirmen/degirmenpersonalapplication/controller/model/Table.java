package com.degirmen.degirmenpersonalapplication.controller.model;

public class Table {
  public String id;
  public TableStatus status;
  public String title;
  public String userName;
  public Integer zakazId;

  public Table() {
  }

  public Table(TableStatus status, String title, String userName) {
    this.status = status;
    this.title = title;
    this.userName = userName;
  }
}