package com.degirmen.degirmenpersonalapplication.db.dao;

import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;
import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.model.TableStatus;
import com.degirmen.degirmenpersonalapplication.db.util.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableDao {


  public List<Table> getTableList() throws SQLException {
    List<Table> tables = new ArrayList<>();
    try (PreparedStatement ps = ConnectionUtil.getInstance().getConnection().prepareStatement(getTableListQuery())) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) tables.add(fromRs(rs));
      }
    }
    return tables;
  }

  private Table fromRs(ResultSet rs) throws SQLException {
    Table table = new Table();
    String tableID = rs.getString("TableID");
    table.id = tableID;
    if (tableID != null) {
      if (Singleton.getInstance().getUser().id.equals(rs.getInt("UserID"))) {
        table.status = TableStatus.MY;
        table.zakazId = rs.getInt("ID");
      }
      else {
        table.status = TableStatus.FOREIGN;
        table.userName = rs.getString("UserName");
      }
    } else {
      table.status = TableStatus.FREE;
    }
    table.title = rs.getString("Name");
    return table;
  }

  private String getTableListQuery() {
    return "SELECT Tables.[Name], TableID, UserID, [User].[Name] AS UserName, ZakazVirtual.ID " +
            "FROM Tables " +
            "       LEFT JOIN ZakazVirtual ON Tables.[Name] = ZakazVirtual.TableID AND ZakazVirtual.Active = 1 " +
            "       LEFT JOIN [User] ON ZakazVirtual.UserID = [User].ID;";
  }
}
