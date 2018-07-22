package com.degirmen.degirmenpersonalapplication.client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.degirmen.degirmenpersonalapplication.R;


public class OptionActivity extends AppCompatActivity {

  private Toolbar toolbar;
  private String titleString;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_option);

    titleString = getIntent().getStringExtra("title");

    initToolbar();
    initImageView();
    setSupportActionBar(toolbar);
    setDisplayHomeAsUpEnabled();
  }

  private void initImageView() {
    ImageView orderImageView = findViewById(R.id.orderImageView);
    orderImageView.setOnClickListener(view -> {
      Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
      if (titleString != null) intent.putExtra("title", titleString);
      startActivity(intent);
    });
  }


  private void initToolbar() {
    toolbar = findViewById(R.id.toolbar);
    toolbar.setNavigationOnClickListener(v -> finish());
  }

  private void setDisplayHomeAsUpEnabled() {
    if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }
}
