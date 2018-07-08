package com.degirmen.degirmenpersonalapplication.client.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.degirmen.degirmenpersonalapplication.R;


public class OptionActivity extends AppCompatActivity {

  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_option);

    initToolbar();
    setSupportActionBar(toolbar);
    setDisplayHomeAsUpEnabled();
  }

  private void initToolbar() {
    toolbar = findViewById(R.id.toolbar);
    toolbar.setNavigationOnClickListener(v -> finish());
  }

  private void setDisplayHomeAsUpEnabled() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }
}
