package com.degirmen.degirmenpersonalapplication.controller.model;

public class ProductOrder {
  public Product product;
  public int count;

  public ProductOrder(Product product, int count) {
    this.product = product;
    this.count = count;
  }
}