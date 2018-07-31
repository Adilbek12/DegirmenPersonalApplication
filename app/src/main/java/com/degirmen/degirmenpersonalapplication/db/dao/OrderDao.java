package com.degirmen.degirmenpersonalapplication.db.dao;

import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrderStatus;
import com.degirmen.degirmenpersonalapplication.db.util.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
//
//  public boolean toOrder(Order order, Integer userId) {
//    try (PreparedStatement ps = ConnectionUtil.getInstance().getConnection().prepareStatement(getInsertZakazVirtualQuery())) {
//      setParam(ps, userId, userId, order.table, getTotalPrice(order.productOrders), getTotalPrice(order.productOrders));
//      try (ResultSet rs = ps.executeQuery()) {
//        if (rs.next()) {
//          Integer id = rs.getInt("val");
//          for (ProductOrder po : order.productOrders)
//            exec(getInsertZakazVirtualProductQuery(), userId, po.product.id, id, po.product.price, po.product.price, po.count, po.product.price);
//        }
//      }
//    } catch (SQLException e) {
//      Log.e(OrderDao.class.getName(), e.getMessage());
//      return false;
//    }
//    return true;
//  }
//
//
//  private Integer getTotalPrice(List<ProductOrder> products) {
//    int sum = 0;
//    for (ProductOrder productOrder : products) {
//      sum += productOrder.product.price * productOrder.count;
//    }
//    return sum;
//  }
//
//  private String getInsertZakazVirtualProductQuery() {
//    return "UPDATE s SET val=val+1 WHERE id=12;\n" +
//      "INSERT INTO ZakazMenuVirtual(ID, UserID, CategoryID, ZakazID, IniPrice, Price, Count, PriceSold, Active, StartTime, Serviced, HasProp, Gruppa, Host, Skidka, ForSkidka)\n" +
//      "  SELECT Val, ?, ?, ?, ?, ?, ?, ?, 1, getdate(), 1, 0, 1, 'MOBILE_APP', 0, 1 FROM s WHERE id=12;";
//  }
//
//  private String getInsertZakazVirtualQuery() {
//    return "UPDATE s SET val=val+1 WHERE id=11;" +
//      "INSERT INTO ZakazVirtual(ID, UserID, UserID2, TableID, TotalPrice, Total, Active, StartTime, ToDate, Counter, TotalObs, Oplata, Tarif, ClientCount, Zadatok, Host, Deposit, Nal, OverPayProc, UnderPayProc, OverPayInt, UnderPayInt, ClientPrice)\n" +
//      "  SELECT val, ?, ?, ?, ?, ?, 1, GETDATE(), GETDATE(),  '***', 0, 1, 1, 0, 0, 'MOBILE_APP', 0, 0, 10, 0, 0, 0, 0 FROM [s] WHERE id=11;" +
//      "SELECT val FROM [s] WHERE id=11;";
//  }
//
//
//  private boolean exec(String sql, Object... params) {
//    try (PreparedStatement statement = ConnectionUtil.getInstance().getConnection().prepareStatement(sql)) {
//      setParam(statement, params);
//      statement.execute();
//      return true;
//    } catch (SQLException e) {
//      Log.e(OrderDao.class.getName(), e.getNextException().toString());
//      return false;
//    }
//  }
//
//  private void setParam(PreparedStatement statement, Object... params) throws SQLException {
//    for (int i = 0; i < params.length; i++) statement.setObject(i + 1, params[i]);
//  }

  public List<ProductOrder> getOrders(Integer userId, String tableId) {
    Log.wtf("TAG", userId + " " + tableId);
    List<ProductOrder> productOrderList = new ArrayList<>();
    try (PreparedStatement statement = ConnectionUtil.getInstance().getConnection().prepareStatement(getSearchQuery())) {
      statement.setObject(1, userId);
      statement.setObject(2, tableId);
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) productOrderList.add(fromRs(resultSet));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return productOrderList;
  }

  private ProductOrder fromRs(ResultSet resultSet) throws SQLException {
    ProductOrder productOrder = new ProductOrder();
    productOrder.count = resultSet.getInt("Count");
    productOrder.status = ProductOrderStatus.OLD;
    productOrder.comment = resultSet.getString("Comments");
    productOrder.product = new Product(resultSet.getInt("CategoryID"), null, resultSet.getString("Name"), (int) resultSet.getFloat("Price"));
    Log.wtf("TAG", productOrder.toString());
    return productOrder;
  }

  private String getSearchQuery() {
    return "SELECT t2.ID,t2.CategoryID,t2.[Count],t2.Price,t2.Comments, t1.Name, t2.zakazid, t1.Price FROM Category t1\n" +
      "  INNER JOIN [zakazmenuvirtual] t2 ON t1.ID = t2.CategoryID\n" +
      "WHERE t2.zakazid=(SELECT ID FROM [ZakazVirtual] WHERE EndTime IS NULL AND UserID=? AND TableID=?);";
  }
}