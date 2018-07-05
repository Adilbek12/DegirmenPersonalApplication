package com.degirmen.degirmenpersonalapplication.db.register_impl;

import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.UserRegister;
import com.degirmen.degirmenpersonalapplication.db.dao.UserDao;

import java.util.List;

public class UserRegisterImpl implements UserRegister {

  private UserDao userDao;

  public UserRegisterImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public List<User> getUsers() {
    return userDao.getUsers();
  }
}
