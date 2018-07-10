package com.degirmen.degirmenpersonalapplication.client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.controller.controller.RegisterController;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;
import com.degirmen.degirmenpersonalapplication.controller.model.User;
import com.degirmen.degirmenpersonalapplication.controller.register.AuthRegister;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

  private AutoCompleteTextView nameAuthCompleteTextView;
  private EditText passwordEditText;

  private AuthRegister userRegister;
  private ProgressBar progressBar;
  private List<User> userList;
  private ArrayAdapter<String> adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    progressBar = findViewById(R.id.progressBar);
    userRegister = RegisterController.getInstance().getAuthRegister();

    initInputText();
  }

  private String[] toSuggester(List<User> users) {
    String[] array = new String[users.size()];
    for (int i = 0; i < users.size(); i++) array[i] = users.get(i).name;
    return array;
  }


  private void initInputText() {

    nameAuthCompleteTextView = findViewById(R.id.name_auth_complete_text_view);
    passwordEditText = findViewById(R.id.password_edit_text);
    passwordEditText.setOnEditorActionListener(getOnEditorActionListener());
    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<>());

    progressBar.setVisibility(View.VISIBLE);
    userRegister.getUsers(userList -> {
      adapter = getNameAdapter(userList);

    });
    nameAuthCompleteTextView.setAdapter(adapter);
    progressBar.setVisibility(View.GONE);
  }


  private ArrayAdapter<String> getNameAdapter(List<User> userList) {
    return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toSuggester(userList));
  }

  private TextView.OnEditorActionListener getOnEditorActionListener() {
    return (textView, i, keyEvent) -> {
      toSignInActivity();
      return true;
    };
  }

  public void buttonClicked(View view) {
    switch (view.getId()) {
      case R.id.sign_in_button:
        toSignInActivity();
        break;
      case R.id.setting_button:
        toSettingActivity();
        break;
    }
  }

  private void toSettingActivity() {
    startActivity(new Intent(getApplicationContext(), SettingActivity.class));
  }

  private void toSignInActivity() {
    String name = nameAuthCompleteTextView.getText().toString();
    String password = passwordEditText.getText().toString();

    userRegister.auth(name, password, user -> {
      if (user == null) {
        alertWrongMessage();
        progressBar.setVisibility(View.GONE);
      } else {
        Singleton.getInstance().saveUser(user);
        intentToNextActivity();
        progressBar.setVisibility(View.GONE);
      }
    });
  }

  private void intentToNextActivity() {
    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
  }

  private void alertWrongMessage() {
    Toast.makeText(getApplicationContext(),
      "Неправильный пользователь или пароль!", Toast.LENGTH_SHORT).show();
  }
}

