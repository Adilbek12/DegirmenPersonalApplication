package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductType;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;
import com.degirmen.degirmenpersonalapplication.db.dao.ProductDao;
import com.degirmen.degirmenpersonalapplication.db.util.ConnectionUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRegisterImpl implements ProductRegister {

  private ProductDao productDao;

  public ProductRegisterImpl() {
    this.productDao = new ProductDao(ConnectionUtil.createConnection());
  }

  @Override
  public List<ProductType> getProductTypeList() {
    return getProductType();
  }

  @Override
  public List<ProductCategory> getProductCategory(ProductType gc) {
    try {
      return productDao.getSubCategoryList(gc.root);
    } catch (SQLException e) {
      Log.e(e.getMessage(), ProductRegisterImpl.class.getName());
    }
    return null;
  }

  @Override
  public List<Product> getProductList(Integer id) {
    try {
      return productDao.getProductList(id);
    } catch (SQLException e) {
      Log.e(e.getMessage(), ProductRegisterImpl.class.getName());
    }
    return null;
  }

  @Override
  public List<Product> searchProduct(String name) {
    try {
      return productDao.searchProduct(name);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private List<ProductType> getProductType() {
    List<ProductType> generalCategories = new ArrayList<>();
    generalCategories.add(new ProductType(1));
    generalCategories.add(new ProductType(2));
    generalCategories.add(new ProductType(3));
    generalCategories.add(new ProductType(5));
    generalCategories.add(new ProductType(6));
    generalCategories.add(new ProductType(7));
    return generalCategories;
  }
}
