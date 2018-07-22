package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.support.annotation.NonNull;

import com.degirmen.degirmenpersonalapplication.controller.model.NewTableCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.Order;
import com.degirmen.degirmenpersonalapplication.controller.model.OrderCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrderCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.OrderRegister;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonService;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonServiceGenerator;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class OrderRegisterImpl implements OrderRegister {

  @Override
  public void toOrder(Order order, User user, String table, Callback<Boolean> callback) {
    postNewTable(user.id, 1, 2, table, newTableCopy -> {
      Integer zakazId = Integer.valueOf(newTableCopy.id);
      saveProducts(zakazId, toJsonArray(order.productOrders));
      printCheck(zakazId, success -> {});
      callback.doSomething(true);
    });
  }

  private void printCheck(Integer zakazId, Callback<Boolean> callback) {
    JsonService printService = JsonServiceGenerator.createService(JsonService.class);
    Call<List<NewTableCopy>> printCall = printService.printCehck("print_check", zakazId);
    printCall.enqueue(new retrofit2.Callback<List<NewTableCopy>>() {
      @Override
      public void onResponse(@NonNull Call<List<NewTableCopy>> call, @NonNull Response<List<NewTableCopy>> response) {
        if (response.body() != null) {
          callback.doSomething(!response.body().isEmpty());
        } else {
          callback.doSomething(false);
        }
      }

      @Override
      public void onFailure(@NonNull Call<List<NewTableCopy>> call, @NonNull Throwable throwable) {
        callback.doSomething(false);
      }
    });
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


  private void saveProducts(Integer zakazId, JsonArray jsonArray) {
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
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    };
    thread.start();
  }

  private void postNewTable(Integer userId, Integer clientCount, Integer tarif, String table, Callback<NewTableCopy> callback) {
    JsonService newTableService = JsonServiceGenerator.createService(JsonService.class);
    Call<List<NewTableCopy>> newTableCall = newTableService.newTable("new_zakaz", userId, clientCount, tarif, table);
    newTableCall.enqueue(new retrofit2.Callback<List<NewTableCopy>>() {
      @Override
      public void onResponse(@NonNull Call<List<NewTableCopy>> call, @NonNull Response<List<NewTableCopy>> response) {
        List<NewTableCopy> newTableCopies = Objects.requireNonNull(response.body());
        if (!newTableCopies.isEmpty()) {
          callback.doSomething(newTableCopies.get(0));
        } else {
          callback.doSomething(null);
        }
      }

      @Override
      public void onFailure(@NonNull Call<List<NewTableCopy>> call, @NonNull Throwable throwable) {
        callback.doSomething(null);
      }
    });
  }

  @Override
  public void getOrders(User user, Table table, Callback<List<ProductOrder>> callback) {
    getOrderCopyList(user.id, orderCopyList -> {
      for (OrderCopy orderCopy : orderCopyList) {
        if (table.id.equals(Integer.parseInt(orderCopy.tableID))) {
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
    return productOrder;
  }

  private void getOrderCopyList(Integer userId, Callback<List<OrderCopy>> callback) {
    JsonService orderService = JsonServiceGenerator.createService(JsonService.class);
    Call<List<OrderCopy>> ordersCall = orderService.getOrderCopyList("zakaz", userId);
    ordersCall.enqueue(new retrofit2.Callback<List<OrderCopy>>() {
      @Override
      public void onResponse(@NonNull Call<List<OrderCopy>> call, @NonNull Response<List<OrderCopy>> response) {
        callback.doSomething(response.body());
      }

      @Override
      public void onFailure(@NonNull Call<List<OrderCopy>> call, @NonNull Throwable t) {
        callback.doSomething(new ArrayList<>());
      }
    });
  }

  private void getProductOrderCopyList(Integer zakazId, Callback<List<ProductOrderCopy>> callback) {
    JsonService productOrderService = JsonServiceGenerator.createService(JsonService.class);
    Call<List<ProductOrderCopy>> productOrderCall = productOrderService.getProductOrderList("products", zakazId);
    productOrderCall.enqueue(new retrofit2.Callback<List<ProductOrderCopy>>() {
      @Override
      public void onResponse(@NonNull Call<List<ProductOrderCopy>> call, @NonNull Response<List<ProductOrderCopy>> response) {
        callback.doSomething(response.body());
      }

      @Override
      public void onFailure(@NonNull Call<List<ProductOrderCopy>> call, @NonNull Throwable throwable) {
        callback.doSomething(new ArrayList<>());
      }
    });
  }
}