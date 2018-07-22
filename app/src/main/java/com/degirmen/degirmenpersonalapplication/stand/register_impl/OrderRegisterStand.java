package com.degirmen.degirmenpersonalapplication.stand.register_impl;

import com.degirmen.degirmenpersonalapplication.controller.model.Order;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.OrderRegister;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;

import java.util.List;

public class OrderRegisterStand implements OrderRegister {

  @Override
  public void toOrder(Order order, User forUser, String table, Callback<Boolean> callback) {

  }

  @Override
  public void getOrders(User user, Table table, Callback<List<ProductOrder>> cal) {

  }
}
