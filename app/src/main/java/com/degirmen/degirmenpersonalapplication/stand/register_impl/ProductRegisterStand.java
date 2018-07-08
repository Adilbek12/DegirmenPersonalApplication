package com.degirmen.degirmenpersonalapplication.stand.register_impl;

import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductType;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;

import java.util.ArrayList;
import java.util.List;

public class ProductRegisterStand implements ProductRegister {
  @Override
  public List<ProductType> getProductTypeList() {
    return getProductType();
  }

  @Override
  public List<ProductCategory> getProductCategory(ProductType gc) {
    List<ProductCategory> productCategories = new ArrayList<>();
    for (ProductCategory pc : getProductCategoryList())
      if (pc.parent.equals(gc.root)) productCategories.add(pc);
    return productCategories;
  }

  @Override
  public List<Product> getProductList(Integer sc) {
    return getProductList();
  }

  @Override
  public List<Product> searchProduct(String name) {
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
    for (int j = 0; j < getProductTypeList().size(); j++) {
      for (int i = 1; i <= 10; i++) {
        products.add(new Product(i, getProductCategoryList().get(j).id, "fdsfsfsd", 123));
      }
    }
    return products;
  }
}
