package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.NewTableCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.Order;
import com.degirmen.degirmenpersonalapplication.controller.model.OrderCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrderCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrderStatus;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;
import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.model.UserCopy;
import com.degirmen.degirmenpersonalapplication.controller.register.OrderRegister;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonService;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonServiceGenerator;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.DataOutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class OrderRegisterImpl implements OrderRegister {
  private static final String TAG = "OrderRegisterImpl";

  @Override
  public void toOrder(Order order, User user, String table, Callback<Boolean> callback) {
    postNewTable(user.id, 1, 2, table, newTableCopy -> {
      Integer zakazId = Integer.valueOf(newTableCopy.id);
      saveProducts(zakazId, toJsonArray(order.productOrders), success -> {
        if (success) {
          printCheck(zakazId, s -> {});
        }
      });
      callback.doSomething(true);
    });
  }

  private void printCheck(Integer zakazId, Callback<Boolean> callback) {
    JsonService printService = JsonServiceGenerator.createService(JsonService.class);
    Call<String> printCall = printService.printCehck("", zakazId);
    printCall.enqueue(new retrofit2.Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        callback.doSomething(true);
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {
        callback.doSomething(false);

      }
    });
  }

  private JsonArray toJsonArray(ProductOrder productOrder) {
    return toJsonArray(new ArrayList<ProductOrder>() {{add(productOrder);}});
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
    String urlAdress = "http://10.64.0.10/json/?command=saveproducts&zakazid=" + zakazId + "&products=" + new Gson().toJson(jsonArray);
    Thread thread = new Thread() {
      @Override
      public void run() {
        try {
          URL url = new URL(urlAdress);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestMethod("POST");
          conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
          conn.setRequestProperty("Accept", "application/json");
          conn.setDoOutput(true);
          conn.setDoInput(true);
          DataOutputStream os = new DataOutputStream(conn.getOutputStream());
          os.flush();
          os.close();
          conn.disconnect();
          callback.doSomething(true);
        } catch (Exception e) {
          callback.doSomething(false);
          throw new RuntimeException(e);
        }
      }
    };
    thread.start();
  }

  private void postNewTable(Integer userId, Integer clientCount, Integer tarif, String table, Callback<NewTableCopy> callback) {
    JsonService newTableService = JsonServiceGenerator.createService(JsonService.class);
    Call<String> newTableCopy = newTableService.newTable("new_zakaz", userId, clientCount, tarif, table);
    newTableCopy.enqueue(new retrofit2.Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        String responseString = response.body();
        String[] arrayString = responseString.split("/>");
        if (arrayString.length > 1){
          String jsonString = arrayString[1];
          Type listType = new TypeToken<ArrayList<NewTableCopy>>(){}.getType();
          List<NewTableCopy> tables =
            new GsonBuilder().create().fromJson(jsonString, listType);

          for(NewTableCopy user: tables){
            Log.d(TAG, "onResponse: " + user.toString());
          }
          callback.doSomething(tables.get(0));
        }
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {

      }
    });
  }

  @Override
  public void getOrders(User user, Table table, Callback<List<ProductOrder>> callback) {
    getOrderCopyList(user.id, orderCopyList -> {
      for (OrderCopy orderCopy : orderCopyList) {
        if (table.id.equals(orderCopy.tableID)) {
          getProductOrderCopyList(Integer.valueOf(orderCopy.id), productOrderCopyList -> {
            List<ProductOrder> productOrderList = new ArrayList<>();
            for (ProductOrderCopy productOrderCopy : productOrderCopyList)
              productOrderList.add(fromCopy(productOrderCopy));
            callback.doSomething(productOrderList);
          });
        }
      }
    });
  }

  private ProductOrder fromCopy(ProductOrderCopy productOrderCopy) {
    ProductOrder productOrder = new ProductOrder();
    Product product = new Product();
    productOrder.count = Integer.parseInt(productOrderCopy.count);
    product.id = Integer.valueOf(productOrderCopy.id);
    product.price = Integer.valueOf(productOrderCopy.price);
    product.parent = Integer.valueOf(productOrderCopy.categoryID);
    product.name = productOrderCopy.name;
    productOrder.product = product;
    productOrder.comment = productOrderCopy.comments;
    productOrder.status = ProductOrderStatus.OLD;
    return productOrder;
  }

  private void getOrderCopyList(Integer userId, Callback<List<OrderCopy>> callback) {
    JsonService orderService = JsonServiceGenerator.createService(JsonService.class);
    Call<String> ordersCall = orderService.getOrderCopyList("zakaz", userId);
    ordersCall.enqueue(new retrofit2.Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        String responseString = response.body();
        String[] arrayString = responseString.split("/>");
        if (arrayString.length > 1){
          String jsonString = arrayString[1];
          Type listType = new TypeToken<ArrayList<OrderCopy>>(){}.getType();
          List<OrderCopy> orders =
            new GsonBuilder().create().fromJson(jsonString, listType);

          for(OrderCopy user: orders){
            Log.d(TAG, "onResponse: " + user.toString());
          }
          callback.doSomething(orders);
        }
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {

      }
    });
  }

  private void getProductOrderCopyList(Integer zakazId, Callback<List<ProductOrderCopy>> callback) {
    JsonService productOrderService = JsonServiceGenerator.createService(JsonService.class);
    Call<String> productOrderCall = productOrderService.getProductOrderList("products", zakazId);
    productOrderCall.enqueue(new retrofit2.Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        String responseString = response.body();
        String[] arrayString = responseString.split("/>");
        if (arrayString.length > 1){
          String jsonString = arrayString[1];
          Type listType = new TypeToken<ArrayList<ProductOrderCopy>>(){}.getType();
          List<ProductOrderCopy> products =
            new GsonBuilder().create().fromJson(jsonString, listType);

          for(ProductOrderCopy user: products){
            Log.d(TAG, "onResponse: " + user.toString());
          }
          callback.doSomething(products);
        }
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {

      }
    });
  }
}