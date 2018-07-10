package com.degirmen.degirmenpersonalapplication.db.dao;

import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.Order;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDao {

  private Connection connection;

  public OrderDao(Connection connection) {
    this.connection = connection;
  }

  public boolean toOrder(Order order, Integer userId) {
    return true; //FIXME
  }

  private String getInsertNewOrderForUserQuery() {
    return "";//FIXME
  }

  private boolean exec(String sql, List<Object> params) {
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      setParam(statement, params);
      statement.execute();
      return true;
    } catch (SQLException e) {
      Log.e(OrderDao.class.getName(), e.getMessage());
      return false;
    }
  }

  private void setParam(PreparedStatement statement, List<Object> params) throws SQLException {
    for (int i = 0; i < params.size(); i++) statement.setObject(i + 1, params.get(i));
  }

  public List<ProductOrder> getOrders() {
    return null; //FIXME
  }
}
