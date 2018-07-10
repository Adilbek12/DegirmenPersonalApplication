package com.degirmen.degirmenpersonalapplication.stand.register_impl;

import com.degirmen.degirmenpersonalapplication.controller.model.Order;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.OrderRegister;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;

import java.util.List;

public class OrderRegisterStand implements OrderRegister {

  @Override
  public void toOrder(Order order, User forUser, Callback<Boolean> callback) {
    callback.doSomething(null);
  }

  @Override
  public void getOrders(User user, Callback<List<ProductOrder>> callback) {
    callback.doSomething(null);
  }
}
