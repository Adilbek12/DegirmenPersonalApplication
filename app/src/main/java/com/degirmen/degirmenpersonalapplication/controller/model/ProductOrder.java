package com.degirmen.degirmenpersonalapplication.controller.model;

public class ProductOrder {
  public Product product;
  public int count;
  public String comment;
  public ProductOrderStatus status;

  public ProductOrder() {}

  public ProductOrder(Product product, int count, ProductOrderStatus status) {
    this.product = product;
    this.count = count;
    this.status = status;
  }

  @Override
  public String toString() {
    return "ProductOrder{" +
      "product=" + product +
      ", count=" + count +
      ", comment='" + comment + '\'' +
      ", status=" + status +
      '}';
  }
}
