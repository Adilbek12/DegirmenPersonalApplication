package com.degirmen.degirmenpersonalapplication.stand.register_impl;

import com.degirmen.degirmenpersonalapplication.controller.model.Order;
import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrderStatus;
import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.OrderRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.Register;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class OrderRegisterStand extends Register implements OrderRegister {

  @Override
  public void toOrder(Order order, User forUser, String table, Callback<Boolean> callback) {
    async(() -> sleep());
  }

  @Override
  public void getOrders(User user, Table table, Callback<List<ProductOrder>> cal) {
    List<ProductOrder> productOrderList = new ArrayList<>();
    productOrderList.add(new ProductOrder(getProduct(), 1, ProductOrderStatus.OLD));
    productOrderList.add(new ProductOrder(getProduct(), 1, ProductOrderStatus.OLD));
    productOrderList.add(new ProductOrder(getProduct(), 1, ProductOrderStatus.OLD));
    productOrderList.add(new ProductOrder(getProduct(), 1, ProductOrderStatus.OLD));
    productOrderList.add(new ProductOrder(getProduct(), 1, ProductOrderStatus.OLD));
    productOrderList.add(new ProductOrder(getProduct(), 1, ProductOrderStatus.OLD));
    productOrderList.add(new ProductOrder(getProduct(), 1, ProductOrderStatus.OLD));
    async(() -> {
      sleep();
      cal.doSomething(productOrderList);
    });
  }

  public Product getProduct() {
    Product product = new Product();
    product.id = 1;
    product.name = "123";
    product.parent = 123;
    product.price = 123;
    return product;
  }
}
