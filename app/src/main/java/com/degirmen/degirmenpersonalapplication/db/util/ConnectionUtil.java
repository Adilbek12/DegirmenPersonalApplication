package com.degirmen.degirmenpersonalapplication.db.util;

import com.degirmen.degirmenpersonalapplication.db.config.ConnectionConfig;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

  public Connection createConnection(ConnectionConfig config) throws SQLException {
    return DriverManager.getConnection(config.url(), config.user(), config.password());
  }

  public static ConnectionConfig fileToConnectionConfig(File configFile) throws IOException {
    final ConfigData configData = new ConfigData();
    configData.loadFromFile(configFile);
    return new ConnectionConfig() {
      @Override
      public String url() {
        return configData.get("url");
      }

      @Override
      public String user() {
        return configData.get("user");
      }

      @Override
      public String password() {
        return configData.get("password");
      }
    };
  }
}
