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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonService {

  @GET("/json/")
  Call<String> getUserList(
    @Query("obj") String users,
    @Query("counter") Integer counter
  );

  @GET("/json/")
  Call<String> getTableCopyListCall(
    @Query("obj") String tables
  );

  @GET("/json/")
  Call<String> getOrderCopyList(
    @Query("obj") String zakaz,
    @Query("userid") Integer userId
  );

  @GET("/json/")
  Call<String> getProducts(
    @Query("obj") String getProducts,
    @Query("categoryid") Integer categoryId
  );

  @GET("/json/")
  Call<String> getCategory(
    @Query("obj") String category,
    @Query("parent") Integer parent
  );

  @GET("/json/")
  Call<String> getProductOrderList(
    @Query("obj") String products,
    @Query("zakazid") Integer zakazId
  );

  @POST("/json/")
  Call<String> newTable(
    @Query("command") String newZakaz,
    @Query("userid") Integer userId,
    @Query("clientcount") Integer clientCount,
    @Query("tarif") Integer tarif,
    @Query("tableid") String table
  );

  @POST("/json/")
  Call<String> printCehck(
    @Query("command") String print_check,
    @Query("zakazid") Integer zakazId
  );

  @POST("/json/?command=saveproducts&zakazid={id}")
  Call<String> saveProducts(
    @Query("command") String saveproducts,
    @Query("zakazid") String zakazid,
    @Path("id") String zakazId
  );

}
