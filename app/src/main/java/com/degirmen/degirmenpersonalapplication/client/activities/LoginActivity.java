package com.degirmen.degirmenpersonalapplication.client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

  private ProgressBar progressBar;
  private View darkView;

  private AuthRegister userRegister;
  private ArrayAdapter<String> adapter;

  private List<String> userNameList = new ArrayList<>();

  private Button signInButton;
  private ImageButton settingButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);


    Singleton.getInstance().saveContext(getApplicationContext());

    userRegister = RegisterController.getInstance().getAuthRegister();

    darkView = findViewById(R.id.darkView);
    progressBar = findViewById(R.id.progressBar);
    signInButton = findViewById(R.id.sign_in_button);
    settingButton = findViewById(R.id.setting_button);
    userRegister = RegisterController.getInstance().getAuthRegister();

    initInputText();
  }

  private List<String> toSuggester(List<User> userList) {
    List<String> array = new ArrayList<>();
    for (User user : userList) array.add(user.name);
    return array;
  }


  private void initInputText() {
    nameAuthCompleteTextView = findViewById(R.id.name_auth_complete_text_view);
    passwordEditText = findViewById(R.id.password_edit_text);
    passwordEditText.setOnEditorActionListener(getOnEditorActionListener());
    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userNameList);
    userRegister.getUsers(this::setUserList);
    nameAuthCompleteTextView.setAdapter(adapter);
  }

  private void loading(boolean l) {
    darkView.setVisibility(l ? View.VISIBLE : View.GONE);
    progressBar.setVisibility(l ? View.VISIBLE : View.GONE);
    signInButton.setEnabled(!l);
    passwordEditText.setEnabled(!l);
    nameAuthCompleteTextView.setEnabled(!l);
    settingButton.setEnabled(!l);
  }

  private void setUserList(List<User> userList) {
    runOnUiThread(() -> {
      userNameList = toSuggester(userList);
      adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userNameList);
      nameAuthCompleteTextView.setAdapter(adapter);
    });
  }

  private TextView.OnEditorActionListener getOnEditorActionListener() {
    return (textView, i, keyEvent) -> {
      loading(true);
      toSignInActivity();
      return true;
    };
  }

  public void buttonClicked(View view) {
    loading(true);
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
    loading(false);
    startActivity(new Intent(getApplicationContext(), SettingActivity.class));
  }

  private void toSignInActivity() {
    String name = nameAuthCompleteTextView.getText().toString();
    String password = passwordEditText.getText().toString();

    userRegister.auth(name, password, user ->
      runOnUiThread(() -> {
        loading(false);
        if (user == null) {
          alertWrongMessage();
        } else {
          Singleton.getInstance().saveUser(user);
          intentToNextActivity();
        }
      })
    );
  }

  private void intentToNextActivity() {
    startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
  }

  private void alertWrongMessage() {
    Toast.makeText(getApplicationContext(),
      "Неправильный пользователь или пароль!", Toast.LENGTH_SHORT).show();
  }
}

