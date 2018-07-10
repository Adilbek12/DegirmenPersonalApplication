package com.degirmen.degirmenpersonalapplication.client.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.client.adapters.OrderAdapter;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;

public class OrderActivity extends AppCompatActivity implements OrderAdapter.OnRemoveListener {

  @Override
  public void removeAt(int position) {
    Singleton.getInstance().removeAt(position);
    adapter.notifyDataSetChanged();
  }

  private OrderAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order);

    initToolbar();
    initListView();
  }

  private void initListView() {
    ListView listView = findViewById(R.id.orderListView);
    adapter = new OrderAdapter(this, Singleton.getAll());
    adapter.onRemoveListener = this;
    listView.setAdapter(adapter);
  }

  private void initToolbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setDisplayHomeAsUpEnabled();
    toolbar.setNavigationOnClickListener(v -> finish());
  }

  private void setDisplayHomeAsUpEnabled() {
    try {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    } catch (NullPointerException e) {
      Log.e(OrderActivity.class.getName(), e.getMessage());
    }
  }
}