package com.degirmen.degirmenpersonalapplication.controller.model;

public class Product {
  public Integer id;
  public Integer parent;
  public String name;
  public Integer price;

  public Product() {
  }

  public Product(Integer id, Integer parent, String name, Integer price) {
    this.id = id;
    this.parent = parent;
    this.name = name;
    this.price = price;
  }
}