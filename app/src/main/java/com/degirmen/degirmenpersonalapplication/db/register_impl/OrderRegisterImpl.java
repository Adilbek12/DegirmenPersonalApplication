package com.degirmen.degirmenpersonalapplication.db.register_impl;

import com.degirmen.degirmenpersonalapplication.controller.model.Order;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.OrderRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.Register;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;
import com.degirmen.degirmenpersonalapplication.db.dao.OrderDao;
import com.degirmen.degirmenpersonalapplication.db.util.ConnectionUtil;

import java.util.List;

public class OrderRegisterImpl extends Register implements OrderRegister {

  private OrderDao orderDao;

  public OrderRegisterImpl() {
    this.orderDao = new OrderDao(new ConnectionUtil().getConnection());
  }

  @Override
  public void toOrder(Order order, User forUser, Callback<Boolean> callback) {
    async(() -> callback.doSomething(orderDao.toOrder(order, forUser.id)));
  }

  @Override
  public void getOrders(User user, Callback<List<ProductOrder>> callback) {
    async(() -> callback.doSomething(orderDao.getOrders()));
  }
}