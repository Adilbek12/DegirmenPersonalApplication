package com.degirmen.degirmenpersonalapplication.stand.register_impl;

import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.AuthRegister;

import java.util.ArrayList;
import java.util.List;

public class AuthRegisterStand implements AuthRegister {

  @Override
  public List<User> getUsers() {
    return getUsersList();
  }

  private List<User> getUsersList() {
    List<User> users = new ArrayList<>();
    users.add(new User(1, 1, "ELDOR 1"));
    users.add(new User(2, 2, "ELDOR 2"));
    users.add(new User(3, 3, "ELDOR 3"));
    users.add(new User(4, 4, "ELDOR 4"));
    users.add(new User(5, 5, "ELDOR 5"));
    users.add(new User(6, 6, "ELDOR 6"));
    users.add(new User(7, 7, "ELDOR 7"));
    users.add(new User(8, 8, "ELDOR 8"));
    return users;
  }

  @Override
  public User auth(String user, String password) {
    if (user.equals("ELDOR 1") && password.equals("CHERT")) {
      return new User(1, 1, "ELDOR 1");
    }
    return null;
  }
}
