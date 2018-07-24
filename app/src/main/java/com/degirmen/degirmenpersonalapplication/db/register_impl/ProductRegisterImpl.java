package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategoryCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrderCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductType;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.Register;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonService;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonServiceGenerator;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;
import com.degirmen.degirmenpersonalapplication.db.dao.ProductDao;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ProductRegisterImpl extends Register implements ProductRegister {

  private static final String TAG = "ProductRegisterImpl";
  private ProductDao productDao;

  public ProductRegisterImpl() {
    this.productDao = new ProductDao();
  }

  @Override
  public void getProductTypeList(Callback<List<ProductType>> callback) {
    callback.doSomething(getProductType());
  }

  @Override
  public void getProductCategory(ProductType pt, Callback<List<ProductCategory>> callback) {
    getCategoryCopyList(pt.root, categoryCopyList -> {
      List<ProductCategory> productCategoryList = new ArrayList<>();
      for (ProductCategoryCopy productCategoryCopy : categoryCopyList)
        productCategoryList.add(fromCopy(productCategoryCopy));
      callback.doSomething(productCategoryList);
    });
  }

  private ProductCategory fromCopy(ProductCategoryCopy productCategoryCopy) {
    ProductCategory productCategory = new ProductCategory();
    productCategory.id = Integer.valueOf(productCategoryCopy.id);
    productCategory.name = productCategoryCopy.name;
    productCategory.parent = Integer.valueOf(productCategoryCopy.parent);
    return productCategory;
  }

  private void getCategoryCopyList(Integer root, Callback<List<ProductCategoryCopy>> callback) {
    JsonService categoryService = JsonServiceGenerator.createService(JsonService.class);
    Call<String> categoryCall = categoryService.getCategory("category", root);
    categoryCall.enqueue(new retrofit2.Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        String responseString = response.body();
        String[] arrayString = responseString.split("/>");
        if (arrayString.length > 1){
          String jsonString = arrayString[1];
          Type listType = new TypeToken<ArrayList<ProductCategoryCopy>>(){}.getType();
          List<ProductCategoryCopy> categories =
            new GsonBuilder().create().fromJson(jsonString, listType);

          for(ProductCategoryCopy user: categories){
            Log.d(TAG, "onResponse: " + user.toString());
          }
          callback.doSomething(categories);
        }
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {

      }
    });
  }

  @Override
  public void getProductList(Integer id, Callback<List<Product>> callback) {
    getProductsCopy(id, productsCopy -> {
      List<Product> products = new ArrayList<>();
      for (ProductCopy productCopy : productsCopy) products.add(fromCopy(productCopy));
      callback.doSomething(products);
    });
  }

  private Product fromCopy(ProductCopy productCopy) {
    Product product = new Product();
    product.id = Integer.valueOf(productCopy.id);
    product.name = productCopy.name;
    product.parent = Integer.valueOf(productCopy.parent);
    product.price = Integer.valueOf(productCopy.price);
    return product;
  }


  private void getProductsCopy(Integer categoryId, Callback<List<ProductCopy>> callback) {
    JsonService productService = JsonServiceGenerator.createService(JsonService.class);
    Call<String> productCall = productService.getProducts("getProducts", categoryId);
    productCall.enqueue(new retrofit2.Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        String responseString = response.body();
        String[] arrayString = responseString.split("/>");
        if (arrayString.length > 1){
          String jsonString = arrayString[1];
          Type listType = new TypeToken<ArrayList<ProductCopy>>(){}.getType();
          List<ProductCopy> products =
            new GsonBuilder().create().fromJson(jsonString, listType);

          for(ProductCopy product: products){
            Log.d(TAG, "onResponse: " + product.toString());
          }
          callback.doSomething(products);
        }
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {

      }
    });
  }

  @Override
  public void searchProduct(String name, Callback<List<Product>> callback) {
    async(() -> {
      try {
        callback.doSomething(productDao.searchProduct(name));
      } catch (SQLException e) {
        callback.doSomething(null);
      }
    });
  }

  private List<ProductType> getProductType() {
    List<ProductType> generalCategories = new ArrayList<>();
    generalCategories.add(new ProductType(1));
    generalCategories.add(new ProductType(2));
    generalCategories.add(new ProductType(3));
    return generalCategories;
  }
}
