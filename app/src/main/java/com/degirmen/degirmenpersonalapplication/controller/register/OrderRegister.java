package com.degirmen.degirmenpersonalapplication.controller.register;

import com.degirmen.degirmenpersonalapplication.controller.model.Order;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;

import java.util.List;

public interface OrderRegister {

  void toOrder(Order order, User forUser, String table, Callback<Boolean> callback);

  void getOrders(User user, Table table, Callback<List<ProductOrder>> cal);
}