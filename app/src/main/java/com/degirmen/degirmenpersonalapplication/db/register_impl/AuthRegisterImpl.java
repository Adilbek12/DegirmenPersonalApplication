package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.support.annotation.NonNull;

import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.model.UserCopy;
import com.degirmen.degirmenpersonalapplication.controller.register.AuthRegister;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonService;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonServiceGenerator;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AuthRegisterImpl implements AuthRegister {

  @Override
  public void getUsers(Callback<List<User>> callback) {
    getUserCopyList(usersCopy -> {
      List<User> users = new ArrayList<>();
      for (UserCopy userCopy : usersCopy)
        users.add(new User(Integer.parseInt(userCopy.id), userCopy.name));
      callback.doSomething(users);
    });
  }

  @Override
  public void auth(String userName, String password, Callback<User> callback) {
    getUserCopyList(usersCopy -> {
      for (UserCopy userCopy : usersCopy)
        if (userCopy.name.equals(userName) && userCopy.password.equals(password)) {
          callback.doSomething(new User(Integer.parseInt(userCopy.id), userCopy.name));
          break;
        }
    });
  }

  private void getUserCopyList(Callback<List<UserCopy>> callback) {
    JsonService userService = JsonServiceGenerator.createService(JsonService.class);
    Call<List<UserCopy>> usersCall = userService.getUserList();
    usersCall.enqueue(new retrofit2.Callback<List<UserCopy>>() {
      @Override
      public void onResponse(@NonNull Call<List<UserCopy>> call, @NonNull Response<List<UserCopy>> response) {
        callback.doSomething(response.body());
      }

      @Override
      public void onFailure(@NonNull Call<List<UserCopy>> call, @NonNull Throwable throwable) {
        callback.doSomething(new ArrayList<>());
      }
    });
  }
}