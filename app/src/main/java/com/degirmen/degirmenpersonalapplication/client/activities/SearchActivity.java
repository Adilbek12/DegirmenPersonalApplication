package com.degirmen.degirmenpersonalapplication.client.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.client.adapters.OptionAdapter;
import com.degirmen.degirmenpersonalapplication.controller.controller.RegisterController;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
  private static final String TAG = "SearchActivity";
  private Toolbar toolbar;
  private EditText searchEditText;

  private ListView searchListView;
  private OptionAdapter adapter;
  private ArrayList<ProductOrder> products;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    initToolbar();
    initSearch();
    initSearchListView();
  }

  private void initSearchListView() {
    searchListView = findViewById(R.id.searchListView);
    products = new ArrayList<>();
    adapter = new OptionAdapter(this, products);
    searchListView.setAdapter(adapter);
  }

  private void initSearch() {
    searchEditText = findViewById(R.id.searchEditText);
    searchEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        searchTextDidChanged(s.toString());
      }
    });
  }

  private void searchTextDidChanged(String text) {
    Log.d(TAG, "afterTextChanged: " + text);
    ProductRegister register = RegisterController.getInstance().getProductRegister();

    //FIXME INDICATOR
    register.searchProduct(text, list -> {
      for (Product product : list) {
        ProductOrder productOrder = new ProductOrder(product, 1);
        products.add(productOrder);
      }
      adapter.notifyDataSetChanged();
    });
  }

  private void initToolbar() {
    toolbar = findViewById(R.id.toolbar);
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