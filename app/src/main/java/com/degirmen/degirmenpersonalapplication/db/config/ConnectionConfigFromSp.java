package com.degirmen.degirmenpersonalapplication.db.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;

public class ConnectionConfigFromSp implements ConnectionConfig {

  @Override
  public String host() {
    return getString("HOST", "10.64.0.10");
  }

  @Override
  public String port() {
    return getString("PORT", "1433");
  }

  @Override
  public String user() {
    return getString("USER", "mobile");
  }

  @Override
  public String password() {
    return getString("PASSWORD", "ct_max_change");
  }

  @Override
  public void setConfig(String host) {
    SharedPreferences.Editor editor = getSharedPreferences().edit();
    editor.putString("HOST", host);
    editor.apply();
  }

  public static String getString(String key, String def) {
    return getSharedPreferences().getString(key, def);
  }

  private static SharedPreferences getSharedPreferences() {
    return Singleton.getInstance().getContext().getSharedPreferences("CONNECTION_CONFIG", Context.MODE_PRIVATE);
  }
}
