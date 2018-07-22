package com.degirmen.degirmenpersonalapplication.db.dao;

import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;
import com.degirmen.degirmenpersonalapplication.db.util.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

  private String firstUpperCased(String input) {
    if (input.isEmpty()) return input;
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
    try (PreparedStatement ps = ConnectionUtil.getInstance().getConnection().prepareStatement(getSearchProductQuery())) {
      ps.setObject(1, name + "%");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          products.add(productFromRs(rs));
        }
      }
    }
    return products;
  }

  private String getSearchProductQuery() {
    return "SELECT ID, Name, Parent, Price FROM Category WHERE [Name] LIKE ? AND isProduct=1 AND [Name] IS NOT NULL ORDER BY [Name]";
  }
}
