package com.degirmen.degirmenpersonalapplication.controller.register;

import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductType;

import java.util.List;

public interface ProductRegister {
  List<ProductType> getProductTypeList();

  List<ProductCategory> getProductCategory(ProductType gc);

  List<Product> getProductList(Integer id);

  List<Product> searchProduct(String name);
}
