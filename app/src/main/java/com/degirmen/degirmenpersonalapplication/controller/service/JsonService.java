package com.degirmen.degirmenpersonalapplication.controller.service;

import com.degirmen.degirmenpersonalapplication.controller.model.NewTableCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.OrderCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategoryCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrderCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.TableCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.UserCopy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonService {

  @GET("/json/")
  Call<List<UserCopy>> getUserList(
    @Query("obj") String users,
    @Query("counter") Integer counter
  );

  @GET("/json/")
  Call<List<TableCopy>> getTableCopyListCall(
    @Query("obj") String tables,
    @Query("counter") Integer counter
  );

  @GET("/json/")
  Call<List<OrderCopy>> getOrderCopyList(
    @Query("obj") String zakaz,
    @Query("userid") Integer userId,
    @Query("counter") Integer counter
  );

  @GET("/json/")
  Call<List<ProductCopy>> getProducts(
    @Query("obj") String getProducts,
    @Query("categoryid") Integer categoryId,
    @Query("counter") Integer counter
  );

  @GET("/json/")
  Call<List<ProductCategoryCopy>> getCategory(
    @Query("obj") String category,
    @Query("parent") Integer parent,
    @Query("counter") Integer counter
  );

  @GET("/json/")
  Call<List<ProductOrderCopy>> getProductOrderList(
    @Query("obj") String products,
    @Query("zakazid") Integer zakazId,
    @Query("counter") Integer counter
  );

  @POST("/json/")
  Call<List<NewTableCopy>> newTable(
    @Query("command") String newZakaz,
    @Query("userid") Integer userId,
    @Query("clientcount") Integer clientCount,
    @Query("tarif") Integer tarif,
    @Query("tableid") String table,
    @Query("counter") Integer counter
  );

  @POST("/json/")
  Call<List<NewTableCopy>> printCehck(
    @Query("command") String print_check,
    @Query("zakazid") Integer zakazId,
    @Query("counter") Integer counter
  );

}
