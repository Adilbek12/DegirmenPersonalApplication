package com.degirmen.degirmenpersonalapplication.db.dao;

import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
  private Connection connection;

  public UserDao(Connection connection) {
    this.connection = connection;
  }

  public List<User> getUsers() {
    //language=SQLExpress
    return getUsers("SELECT * FROM [User]");
  }

  private User fromRs(ResultSet rs) throws SQLException {
    User user = new User();
    user.id = rs.getInt("ID");
    user.type = rs.getInt("Type");
    user.name = rs.getString("Name");
    return user;
  }

  protected List<User> getUsers(String sql) {
    List<User> users = new ArrayList<>();
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) users.add(fromRs(rs));
      }
    } catch (SQLException e) {
      Log.e(e.getMessage(), UserDao.class.getName());
    }
    return users;
  }
}
