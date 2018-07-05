package com.degirmen.degirmenpersonalapplication.client;

import com.degirmen.degirmenpersonalapplication.controller.register.UserRegister;
import com.degirmen.degirmenpersonalapplication.db.dao.UserDao;
import com.degirmen.degirmenpersonalapplication.db.register_impl.UserRegisterImpl;
import com.degirmen.degirmenpersonalapplication.db.util.ConnectionUtil;

import java.io.IOException;

public class Test {
  public static void main(String[] args) throws IOException {
    UserRegister register = new UserRegisterImpl(new UserDao(ConnectionUtil.createConnection()));
    System.out.println(register.getUsers());
  }
}