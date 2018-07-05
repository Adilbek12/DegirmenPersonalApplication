package com.degirmen.degirmenpersonalapplication.db.util;

import android.util.Log;

import com.degirmen.degirmenpersonalapplication.db.config.ConnectionConfig;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {


  public static Connection createConnection() {
    try {
      String connectionUrl = String.format("%s;user=%s;password=%s", ConnectionConfig.URL, ConnectionConfig.USER, ConnectionConfig.PASSWORD);
      return DriverManager.getConnection(connectionUrl);
    } catch (Exception e) {
      Log.e(e.getMessage(), ConnectionUtil.class.getName());
    }
    return null;
  }
}
