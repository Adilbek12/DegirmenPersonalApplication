package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;
import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.model.UserCopy;
import com.degirmen.degirmenpersonalapplication.controller.register.AuthRegister;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonService;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonServiceGenerator;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AuthRegisterImpl implements AuthRegister {

  private static final String TAG = "AuthRegisterImpl";
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
      for (UserCopy userCopy : usersCopy) {
        if (userCopy.name.equals(userName) && userCopy.password.equals(password)) {
          callback.doSomething(new User(Integer.parseInt(userCopy.id), userCopy.name));
          return;
        }
      }
      callback.doSomething(null);
    });
  }

  private void getUserCopyList(Callback<List<UserCopy>> callback) {
    JsonService userService = JsonServiceGenerator.createService(JsonService.class);
    Call<String> usersCall = userService.getUserList("users", Singleton.getInstance().counter++);
    usersCall.enqueue(new retrofit2.Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        String responseString = response.body();
        String[] arrayString = responseString.split("/>");
        if (arrayString.length > 1){
          String jsonString = arrayString[1];
          Type listType = new TypeToken<ArrayList<UserCopy>>(){}.getType();
          List<UserCopy> users =
            new GsonBuilder().create().fromJson(jsonString, listType);

          for(UserCopy user: users){
            Log.d(TAG, "onResponse: " + user.toString());
          }
          callback.doSomething(users);
        }
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {

      }
    });
  }
}