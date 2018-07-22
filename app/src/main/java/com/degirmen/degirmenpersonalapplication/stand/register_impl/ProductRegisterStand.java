package com.degirmen.degirmenpersonalapplication.stand.register_impl;

import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductType;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.Register;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class ProductRegisterStand extends Register implements ProductRegister {

  @Override
  public void getProductTypeList(Callback<List<ProductType>> callback) {
    callback.doSomething(getProductType());
  }

  @Override
  public void getProductCategory(ProductType gc, Callback<List<ProductCategory>> callback) {
    async(() -> {
      sleep();
      List<ProductCategory> productCategories = new ArrayList<>();
      for (ProductCategory pc : getProductCategoryList())
        if (pc.parent.equals(gc.root)) productCategories.add(pc);
      callback.doSomething(productCategories);
    });
  }

  @Override
  public void getProductList(Integer sc, Callback<List<Product>> callback) {
    async(() -> {
      sleep();
      callback.doSomething(getProductList());
    });
  }

  @Override
  public void searchProduct(String name, Callback<List<Product>> callback) {
    async(() -> {
      sleep();
      callback.doSomething(getProductList());
    });
  }

  private List<ProductType> getProductType() {
    List<ProductType> generalCategories = new ArrayList<>();
    generalCategories.add(new ProductType(1));
    generalCategories.add(new ProductType(2));
    generalCategories.add(new ProductType(3));
    return generalCategories;
  }

  private List<ProductCategory> getProductCategoryList() {
    List<ProductCategory> productCategories = new ArrayList<>();
    productCategories.add(new ProductCategory(1, 1, "NAME 1"));
    productCategories.add(new ProductCategory(2, 1, "NAME 2"));
    productCategories.add(new ProductCategory(3, 1, "NAME 3"));
    productCategories.add(new ProductCategory(4, 2, "NAME 4"));
    productCategories.add(new ProductCategory(5, 2, "NAME 5"));
    productCategories.add(new ProductCategory(6, 2, "NAME 6"));
    productCategories.add(new ProductCategory(7, 3, "NAME 7"));
    productCategories.add(new ProductCategory(8, 3, "NAME 8"));
    productCategories.add(new ProductCategory(9, 3, "NAME 9"));
    productCategories.add(new ProductCategory(10, 5, "NAME 10"));
    productCategories.add(new ProductCategory(11, 5, "NAME 11"));
    productCategories.add(new ProductCategory(12, 5, "NAME 12"));
    productCategories.add(new ProductCategory(13, 6, "NAME 13"));
    productCategories.add(new ProductCategory(14, 6, "NAME 14"));
    productCategories.add(new ProductCategory(15, 6, "NAME 15"));
    productCategories.add(new ProductCategory(16, 7, "NAME 16"));
    productCategories.add(new ProductCategory(17, 7, "NAME 17"));
    productCategories.add(new ProductCategory(18, 7, "NAME 18"));
    return productCategories;
  }

  private List<Product> getProductList() {
    List<Product> products = new ArrayList<>();
    for (int j = 0; j < getProductType().size(); j++) {
      for (int i = 1; i <= 10; i++) {
        products.add(new Product(i, getProductCategoryList().get(j).id, "fdsfsfsd", 123));
      }
    }
    return products;
  }
}
