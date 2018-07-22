package com.degirmen.degirmenpersonalapplication.stand.register_impl;

import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.AuthRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.Register;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class AuthRegisterStand extends Register implements AuthRegister {

  @Override
  public void getUsers(Callback<List<User>> callback) {
    async(() -> {
      sleep();
      callback.doSomething(getUsersList());
    });
  }

  private List<User> getUsersList() {
    List<User> users = new ArrayList<>();
    users.add(new User());
    return users;
  }

  @Override
  public void auth(String userName, String password, Callback<User> userCallback) {
    async(() -> {
      sleep();
      if (userName.equals("ELDOR 1") && password.equals("123")) {
        userCallback.doSomething(new User());
      } else {
        userCallback.doSomething(null);
      }
    });
  }
}