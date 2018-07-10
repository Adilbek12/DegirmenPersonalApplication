package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductType;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.Register;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;
import com.degirmen.degirmenpersonalapplication.db.dao.ProductDao;
import com.degirmen.degirmenpersonalapplication.db.util.ConnectionUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRegisterImpl extends Register implements ProductRegister {

  private ProductDao productDao;

  public ProductRegisterImpl() {
    this.productDao = new ProductDao(new ConnectionUtil().getConnection());
  }

  @Override
  public void getProductTypeList(Callback<List<ProductType>> callback) {
    start(() -> callback.doSomething(getProductType()));
  }

  @Override
  public void getProductCategory(ProductType gc, Callback<List<ProductCategory>> callback) {
    start(() -> {
      try {
        callback.doSomething(productDao.getSubCategoryList(gc.root));
      } catch (SQLException e) {
        callback.doSomething(null);
        Log.e(ProductRegisterImpl.class.getName(), e.getMessage());
      }
    });
  }

  @Override
  public void getProductList(Integer id, Callback<List<Product>> callback) {
    start(() -> {
      try {
        callback.doSomething(productDao.getProductList(id));
      } catch (SQLException e) {
        callback.doSomething(null);
        Log.e(ProductRegisterImpl.class.getName(), e.getMessage());
      }
    });
  }

  @Override
  public void searchProduct(String name, Callback<List<Product>> callback) {
    start(() -> {
      try {
        callback.doSomething(productDao.searchProduct(name));
      } catch (SQLException e) {
        callback.doSomething(null);
        Log.e(ProductRegisterImpl.class.getName(), e.getMessage());
      }
    });
  }

  private List<ProductType> getProductType() {
    List<ProductType> generalCategories = new ArrayList<>();
    generalCategories.add(new ProductType(1));
    generalCategories.add(new ProductType(2));
    generalCategories.add(new ProductType(3));
    generalCategories.add(new ProductType(5));
    return generalCategories;
  }
}
