package com.degirmen.degirmenpersonalapplication.controller.register;

import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductType;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;

import java.util.List;

public interface ProductRegister {
  void getProductTypeList(Callback<List<ProductType>> callback);

  void getProductCategory(ProductType gc, Callback<List<ProductCategory>> callback);

  void getProductList(Integer id, Callback<List<Product>> callback);

  void searchProduct(String name, Callback<List<Product>> callback);
}
