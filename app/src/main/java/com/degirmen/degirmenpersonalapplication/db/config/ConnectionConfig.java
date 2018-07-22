package com.degirmen.degirmenpersonalapplication.db.config;


public interface ConnectionConfig {
  String host();

  String port();

  String user();

  String password();

  void setConfig(String host, String port, String user, String password);
}