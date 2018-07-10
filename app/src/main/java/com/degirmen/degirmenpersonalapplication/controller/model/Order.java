package com.degirmen.degirmenpersonalapplication.controller.model;

import java.util.List;

public class Order {
  public List<ProductOrder> productOrders;
  public String table;

  public Order(List<ProductOrder> productOrders, String table) {
    this.productOrders = productOrders;
    this.table = table;
  }
}
