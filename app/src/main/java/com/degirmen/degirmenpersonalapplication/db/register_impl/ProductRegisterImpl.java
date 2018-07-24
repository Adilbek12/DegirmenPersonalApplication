package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.support.annotation.NonNull;

import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategoryCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductType;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.Register;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonService;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonServiceGenerator;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;
import com.degirmen.degirmenpersonalapplication.db.dao.ProductDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ProductRegisterImpl extends Register implements ProductRegister {

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
    Call<List<ProductCategoryCopy>> categoryCall = categoryService.getCategory("category", root, Singleton.getInstance().counter++);
    categoryCall.enqueue(new retrofit2.Callback<List<ProductCategoryCopy>>() {
      @Override
      public void onResponse(@NonNull Call<List<ProductCategoryCopy>> call, @NonNull Response<List<ProductCategoryCopy>> response) {
        callback.doSomething(response.body());
        categoryCall.cancel();
      }

      @Override
      public void onFailure(@NonNull Call<List<ProductCategoryCopy>> call, @NonNull Throwable t) {
        callback.doSomething(new ArrayList<>());
        categoryCall.cancel();
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
    Call<List<ProductCopy>> productCall = productService.getProducts("getproducts", categoryId, Singleton.getInstance().counter++);
    productCall.enqueue(new retrofit2.Callback<List<ProductCopy>>() {
      @Override
      public void onResponse(@NonNull Call<List<ProductCopy>> call, @NonNull Response<List<ProductCopy>> response) {
        callback.doSomething(response.body());
        productCall.cancel();
      }

      @Override
      public void onFailure(@NonNull Call<List<ProductCopy>> call, @NonNull Throwable t) {
        callback.doSomething(new ArrayList<>());
        productCall.cancel();
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
