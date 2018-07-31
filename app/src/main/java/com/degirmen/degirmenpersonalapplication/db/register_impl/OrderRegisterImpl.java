package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.NewTableCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.Order;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.OrderRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.Register;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonService;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonServiceGenerator;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;
import com.degirmen.degirmenpersonalapplication.db.dao.OrderDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class OrderRegisterImpl extends Register implements OrderRegister {
  private static final String TAG = "OrderRegisterImpl";

  private OrderDao orderDao;

  public OrderRegisterImpl() {
    orderDao = new OrderDao();
  }

  @Override
  public void toOrder(Order order, User user, String table, Integer zakazId, Callback<Boolean> callback) {
    if (zakazId == null) {
      postNewTable(user.id, 1, 2, table, newTableCopy -> {
        Integer newZakazId = Integer.valueOf(newTableCopy.id);
        printCheckFor(newZakazId, order.productOrders, callback);
      });
    } else {
      printCheckFor(zakazId, order.productOrders, callback);
    }
  }

  private void printCheckFor(Integer zakazId, List<ProductOrder> productOrders, Callback<Boolean> callback) {
    saveProducts(zakazId, toJsonArray(productOrders), callback);
    callback.doSomething(true);
  }

  private JsonArray toJsonArray(List<ProductOrder> productOrderList) {
    JsonArray jsonArray = new JsonArray();
    for (ProductOrder productOrder : productOrderList) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("ProductID", String.valueOf(productOrder.product.id));
      jsonObject.addProperty("Amount", String.valueOf(productOrder.count));
      jsonObject.addProperty("Comment", String.valueOf(productOrder.comment));
      jsonArray.add(jsonObject);
    }
    return jsonArray;
  }


  private void saveProducts(Integer zakazId, JsonArray jsonArray, Callback<Boolean> callback) {
    String urlParameters = "command=saveproducts&zakazid=" + (zakazId) + "&products=" + new Gson().toJson(jsonArray);
    String request = "http://10.64.0.10/json/";
    try {
      URL url = new URL(request);
      URLConnection conn = url.openConnection();

      conn.setDoOutput(true);

      OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

      writer.write(urlParameters);
      writer.flush();

      String line;
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
      writer.close();
      reader.close();
      callback.doSomething(true);
    } catch (Exception e) {
      callback.doSomething(false);
      throw new RuntimeException();
    }
  }

  private void postNewTable(Integer userId, Integer clientCount, Integer tarif, String
    table, Callback<NewTableCopy> callback) {
    JsonService newTableService = JsonServiceGenerator.createService(JsonService.class);
    Call<String> newTableCopy = newTableService.newTable("new_zakaz", userId, clientCount, tarif, table);
    newTableCopy.enqueue(new retrofit2.Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        String responseString = response.body();
        String[] arrayString = responseString.split("/>");
        if (arrayString.length > 1) {
          String jsonString = arrayString[1];
          Type listType = new TypeToken<ArrayList<NewTableCopy>>() {}.getType();
          List<NewTableCopy> tables = new GsonBuilder().create().fromJson(jsonString, listType);
          for (NewTableCopy user : tables) {
            Log.d(TAG, "onResponse: " + user.toString());
          }
          callback.doSomething(tables.get(0));
        }
      }

      @Override
      public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

      }
    });
  }

  @Override
  public void getOrders(User user, Table table, Callback<List<ProductOrder>> callback) {
    List<ProductOrder> productOrderList = orderDao.getOrders(user.id, table.id);
    callback.doSomething(productOrderList);
  }
}