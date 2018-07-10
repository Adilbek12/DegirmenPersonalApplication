package com.degirmen.degirmenpersonalapplication.db.dao;

import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

  private Connection connection;

  public ProductDao(Connection connection) {
    this.connection = connection;
  }

  public List<ProductCategory> getSubCategoryList(Integer root) throws SQLException {
    List<ProductCategory> subCategories = new ArrayList<>();
    try (PreparedStatement ps = connection.prepareStatement(getSubCategoryList())) {
      ps.setInt(1, root);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) subCategories.add(subCategoryFromRs(rs));
      }
    }
    return subCategories;
  }

  private String getSubCategoryList() {
    return "SELECT ID, Name, Parent FROM Category WHERE Type=? AND Visible=1 AND IsProduct=0 AND Name IS NOT NULL ORDER BY Name;";
  }

  private ProductCategory subCategoryFromRs(ResultSet rs) throws SQLException {
    ProductCategory sc = new ProductCategory();
    sc.id = rs.getInt("ID");
    sc.name = firstUpperCased(rs.getString("Name"));
    sc.parent = rs.getInt("Parent");
    return sc;
  }

  public List<Product> getProductList(Integer parent) throws SQLException {
    List<Product> products = new ArrayList<>();
    try (PreparedStatement ps = connection.prepareStatement(getProductListQuery())) {
      ps.setInt(1, parent);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) products.add(productFromRs(rs));
      }
    }
    return products;
  }

  private String getProductListQuery() {
    return "SELECT ID, Name, Parent, Price FROM Category WHERE IsProduct=1 AND Visible=1 AND Parent=? ORDER BY Name";
  }

  private String firstUpperCased(String input) {
    return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
  }

  private Product productFromRs(ResultSet rs) throws SQLException {
    Product p = new Product();
    p.id = rs.getInt("ID");
    p.name = firstUpperCased(rs.getString("Name"));
    p.parent = rs.getInt("Parent");
    p.price = (int) rs.getFloat("Price");
    return p;
  }

  public List<Product> searchProduct(String name) throws SQLException {
    List<Product> products = new ArrayList<>();
    try (PreparedStatement ps = connection.prepareStatement(getSearchProductQuery())) {
      ps.setObject(1, name + "%");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) products.add(productFromRs(rs));
      }
    }
    return products;
  }

  public String getSearchProductQuery() {
    return "SELECT ID, Name, Parent, Price FROM Category WHERE Name=? AND Visible=1 AND IsProduct=0 AND Name IS NOT NULL ORDER BY Name";
  }
}
