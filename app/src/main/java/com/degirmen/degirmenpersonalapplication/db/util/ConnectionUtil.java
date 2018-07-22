package com.degirmen.degirmenpersonalapplication.db.util;


import android.os.StrictMode;
import android.util.Log;

import com.degirmen.degirmenpersonalapplication.db.config.ConnectionConfig;
import com.degirmen.degirmenpersonalapplication.db.config.ConnectionConfigFromSp;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil implements Closeable {

  private static Connection connection;

  ConnectionUtil() {
    createConnection();
  }

  public static class ConnectionUtilHolder {
    static final ConnectionUtil HOLDER_INSTANCE = new ConnectionUtil();
  }

  public static ConnectionUtil getInstance() {
    return ConnectionUtilHolder.HOLDER_INSTANCE;
  }

  public Connection getConnection() {
    if (connection == null) createConnection();
    return connection;
  }

  private void createConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      connection = null;
    }
    connect();
  }

  private void connect() {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    try {
      Class.forName("net.sourceforge.jtds.jdbc.Driver");
      ConnectionConfig connectionConfig = new ConnectionConfigFromSp();
      String url = String.format("jdbc:jtds:sqlserver://%s:%s", connectionConfig.host(), connectionConfig.port());
      connection = DriverManager.getConnection(url, connectionConfig.user(), connectionConfig.password());
    } catch (Exception e) {
      Log.e(e.getMessage(), ConnectionUtil.class.getName());
    }
  }

  @Override
  public void close() {
    try {
      connection.close();
      connection = null;
    } catch (SQLException e) {
      Log.e(ConnectionUtil.class.getName(), e.getMessage());
    }
  }
}