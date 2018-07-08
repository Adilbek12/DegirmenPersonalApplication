package com.degirmen.degirmenpersonalapplication.client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.AuthRegister;
import com.degirmen.degirmenpersonalapplication.db.register_impl.AuthRegisterImpl;

public class LoginActivity extends AppCompatActivity {

  private EditText nameEditText;
  private EditText passwordEditText;

  private AuthRegister userRegister;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    initEditText();

    userRegister = new AuthRegisterImpl();
  }

  private void initEditText() {
    nameEditText = findViewById(R.id.name_edit_text);
    passwordEditText = findViewById(R.id.password_edit_text);
  }

  public void buttonClicked(View view) {
    switch (view.getId()) {
      case R.id.sign_in_button:
        signIn();
        break;
      case R.id.setting_button:
        break;
    }
  }

  private void signIn() {
    String name = nameEditText.getText().toString();
    String password = passwordEditText.getText().toString();
    User user = userRegister.auth(name, password);

    if (user == null) {
      alertWrongMessage();
    } else {
      intentToNextActivity();
    }
  }

  private void intentToNextActivity() {
    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
  }

  private void alertWrongMessage() {
    //FIXME FOR ASKAR
  }
}

