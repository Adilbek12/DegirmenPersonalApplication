package com.degirmen.degirmenpersonalapplication.controller.model;

public class Table {
  public Integer id;
  public TableStatus status;
  public String title;

  public Table() {
  }

  public Table(TableStatus status, String title) {
    this.status = status;
    this.title = title;
  }
}