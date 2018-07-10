package com.degirmen.degirmenpersonalapplication.db.register_impl;

import com.degirmen.degirmenpersonalapplication.controller.model.Order;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.OrderRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.Register;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;
import com.degirmen.degirmenpersonalapplication.db.dao.OrderDao;

import java.util.List;

public class OrderRegisterImpl extends Register implements OrderRegister {

  private OrderDao orderDao;

  public OrderRegisterImpl(OrderDao orderDao) {
    this.orderDao = orderDao;
  }


  @Override
  public void toOrder(Order order, User forUser, Callback<Boolean> callback) {
    start(() -> callback.doSomething(orderDao.toOrder(order, forUser.id)));
  }

  @Override
  public void getOrders(User user, Callback<List<ProductOrder>> callback) {
    start(() -> callback.doSomething(orderDao.getOrders()));
  }
}
