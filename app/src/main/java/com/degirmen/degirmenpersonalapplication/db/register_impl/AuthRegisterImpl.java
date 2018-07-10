package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.AuthRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.Register;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;
import com.degirmen.degirmenpersonalapplication.db.dao.AuthDao;
import com.degirmen.degirmenpersonalapplication.db.util.ConnectionUtil;

import java.sql.SQLException;
import java.util.List;

public class AuthRegisterImpl extends Register implements AuthRegister {

  private AuthDao authDao;

  public AuthRegisterImpl() {
    this.authDao = new AuthDao(new ConnectionUtil().getConnection());
  }

  @Override
  public void getUsers(Callback<List<User>> users) {
    async(() -> users.doSomething(authDao.getUsers()));
  }

  @Override
  public void auth(String userName, String password, Callback<User> userCallback) {
    async(() -> {
      try {
        userCallback.doSomething(authDao.getUser(userName, password));
      } catch (SQLException e) {
        userCallback.doSomething(null);
        Log.e(AuthRegisterImpl.class.getName(), e.getMessage());
      }
    });
  }
}