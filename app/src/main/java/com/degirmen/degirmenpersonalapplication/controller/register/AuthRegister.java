package com.degirmen.degirmenpersonalapplication.controller.register;

import com.degirmen.degirmenpersonalapplication.controller.model.User;

import java.util.List;

public interface AuthRegister {

  List<User> getUsers();

  User auth(String user, String password);

}
