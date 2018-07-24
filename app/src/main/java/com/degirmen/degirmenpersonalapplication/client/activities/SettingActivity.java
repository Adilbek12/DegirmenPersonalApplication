package com.degirmen.degirmenpersonalapplication.client.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.db.config.ConnectionConfig;
import com.degirmen.degirmenpersonalapplication.db.config.ConnectionConfigFromSp;

public class SettingActivity extends AppCompatActivity {

  private EditText hostEditText;

  private ConnectionConfig connectionConfig;

  public SettingActivity() {}

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);


    connectionConfig = new ConnectionConfigFromSp();

    initToolbar();
    initEditText();
    initSettingButton();
  }

  private void initToolbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setDisplayHomeAsUpEnabled();
    toolbar.setNavigationOnClickListener(v -> finish());
  }

  private void setDisplayHomeAsUpEnabled() {
    try {
      if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    } catch (NullPointerException e) {
      Log.e(OrderActivity.class.getName(), e.getMessage());
    }
  }

  private void initSettingButton() {
    Button saveButton = findViewById(R.id.save_button);
    saveButton.setOnClickListener(view -> saveConfigs());
  }

  private void initEditText() {
    hostEditText = findViewById(R.id.host_edit_text);
    setTextFromConfigFile();
  }

  private void setTextFromConfigFile() {
    hostEditText.setText(connectionConfig.host());
  }

  private boolean validNewConfig() {
    return validEditText(hostEditText) && hostEditText.getText().toString().matches("^\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b$");
  }

  private boolean validEditText(EditText editText) {
    return editText.getText() != null && editText.getText().length() != 0;
  }

  private void saveConfigs() {
    if (validNewConfig()) {
      String host = hostEditText.getText().toString();
      connectionConfig.setConfig(host);
      alert("Сохранено.");
      finish();
    } else {
      alertWrongMessage();
    }
  }

  private void alertWrongMessage() {
    alert("Укажите корректные данные!");
  }

  private void alert(String message) {
    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
  }
}
