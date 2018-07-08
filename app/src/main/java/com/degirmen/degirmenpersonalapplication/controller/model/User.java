package com.degirmen.degirmenpersonalapplication.controller.model;

public class User {
  public Integer id;
  public Integer type;
  public String name;

  public User() {
  }

  public User(Integer id, Integer type, String name) {
    this.id = id;
    this.type = type;
    this.name = name;
  }
}
