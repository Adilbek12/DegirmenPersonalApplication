package com.degirmen.degirmenpersonalapplication.db.util;

import android.os.StrictMode;
import android.util.Log;

import com.degirmen.degirmenpersonalapplication.db.config.ConnectionConfig;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil implements Closeable {

  private static Connection connection;

  public ConnectionUtil() {
    createConnection();
  }

  public Connection getConnection() {
    return connection;
  }

  private void createConnection() {
    if (connection != null) return;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    try {
      Class.forName("net.sourceforge.jtds.jdbc.Driver");
      connection = DriverManager.getConnection(ConnectionConfig.URL, ConnectionConfig.USER, ConnectionConfig.PASSWORD);
    } catch (Exception e) {
      Log.e(e.getMessage(), ConnectionUtil.class.getName());
    }
  }

  @Override
  public void close() throws IOException {
    try {
      connection.close();
      connection = null;
    } catch (SQLException e) {
      Log.e(ConnectionUtil.class.getName(), e.getMessage());
    }
  }
}
