package com.degirmen.degirmenpersonalapplication.controller.model;

public class ProductCategory {
  public Integer id;
  public Integer parent;
  public String name;

  public ProductCategory() {
  }

  public ProductCategory(Integer id, Integer parent, String name) {
    this.id = id;
    this.parent = parent;
    this.name = name;
  }

  @Override
  public String toString() {
    return "ProductCategory{" +
      "id=" + id +
      ", parent=" + parent +
      ", name='" + name + '\'' +
      '}';
  }
}
