package com.degirmen.degirmenpersonalapplication.controller.register;

import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;

import java.util.List;

public interface AuthRegister {

  void getUsers(Callback<List<User>> obj);

  void auth(String userName, String password, Callback<User> user);

}
