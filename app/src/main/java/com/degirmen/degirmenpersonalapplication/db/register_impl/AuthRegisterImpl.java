package com.degirmen.degirmenpersonalapplication.db.register_impl;

import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.AuthRegister;
import com.degirmen.degirmenpersonalapplication.db.dao.AuthDao;
import com.degirmen.degirmenpersonalapplication.db.util.ConnectionUtil;

import java.sql.SQLException;
import java.util.List;

public class AuthRegisterImpl implements AuthRegister {

  private AuthDao authDao;

  public AuthRegisterImpl() {
    this.authDao = new AuthDao(ConnectionUtil.createConnection());
  }

  @Override
  public List<User> getUsers() {
    return authDao.getUsers();
  }

  @Override
  public User auth(String user, String password) {
    try {
      return authDao.getUser(user, password);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
