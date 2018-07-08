package com.degirmen.degirmenpersonalapplication.db.util;

import android.os.StrictMode;
import android.util.Log;

import com.degirmen.degirmenpersonalapplication.db.config.ConnectionConfig;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {
  public static Connection createConnection() {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    try {
      Class.forName("net.sourceforge.jtds.jdbc.Driver");
      return DriverManager.getConnection(ConnectionConfig.URL, ConnectionConfig.USER, ConnectionConfig.PASSWORD);
    } catch (Exception e) {
      Log.e(e.getMessage(), ConnectionUtil.class.getName());
    }
    throw new UnsupportedOperationException();
  }
}
