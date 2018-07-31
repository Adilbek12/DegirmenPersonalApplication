package com.degirmen.degirmenpersonalapplication.db.dao;

import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.db.util.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthDao {

  private static final String TAG = "AuthDao";

  public List<User> getUsers() {
    return getUsers("SELECT * FROM [User] WHERE Visible=1 AND LEN([Password])>0;");
  }


  private User fromRs(ResultSet rs) throws SQLException {
    User user = new User();
    user.id = rs.getInt("ID");
    user.name = rs.getString("Name");
    Log.d(TAG, "fromRs: " + user.name);
    return user;
  }

  private List<User> getUsers(String sql) {
    List<User> users = new ArrayList<>();
    try (PreparedStatement ps = ConnectionUtil.getInstance().getConnection().prepareStatement(sql)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) users.add(fromRs(rs));
      }
    } catch (SQLException e) {
      Log.e(e.getMessage(), AuthDao.class.getName());
    }
    return users;
  }

  public User getUser(String userName, String password) throws SQLException {
    User user = null;
    try (PreparedStatement ps = ConnectionUtil.getInstance().getConnection().prepareStatement(getSearchQuery())) {
      ps.setObject(1, userName);
      ps.setObject(2, password);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) user = fromRs(rs);
      }
    }
    return user;
  }

  private String getSearchQuery() {
    return "SELECT * FROM [User] WHERE Name=? AND Password=? AND LEN(Password)>0";
  }
}
