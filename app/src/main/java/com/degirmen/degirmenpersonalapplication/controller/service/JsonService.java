package com.degirmen.degirmenpersonalapplication.controller;

import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategoryCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.OrderCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.NewTableCopy;
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

    @GET("/json/?obj=users")
    Call<List<UserCopy>> getUserList();

    @GET("/json/?obj=tables")
    Call<List<TableCopy>> getTableCopyListCall();

    @GET("/json/")
    Call<List<OrderCopy>> getOrderCopyList(
            @Query("obj") String zakaz,
            @Query("userid") Integer userId
    );

    @GET("/json/")
    Call<List<ProductCopy>> getProducts(
            @Query("obj") String getproducts,
            @Query("categoryid") Integer categoryId
    );

    @GET("/json/")
    Call<List<ProductCategoryCopy>> getCategory(
            @Query("obj") String category,
            @Query("parent") Integer parent
    );

    @GET("/json/")
    Call<List<ProductOrderCopy>> getProductOrderList(
            @Query("obj") String products,
            @Query("zakazid") Integer zakazId
    );

    @POST("/json/")
    Call<List<NewTableCopy>> newTable(
            @Query("command") String newZakaz,
            @Query("userid") Integer userId,
            @Query("clientcount") Integer clientCount,
            @Query("tarif") Integer tarif,
            @Query("tableid") String table
    );

    @POST("/json/")
    Call<List<NewTableCopy>> printCehck(
            @Query("command")String print_check,
            @Query("zakazid") Integer zakazId
    );

}
