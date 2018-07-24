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
import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrderStatus;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

  private OptionAdapter adapter;
  private List<ProductOrder> products;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    initToolbar();
    initSearchListView();
    initSearch();
  }

  private void initSearchListView() {
    ListView searchListView = findViewById(R.id.searchListView);
    products = new ArrayList<>();

    adapter = new OptionAdapter(this, products);
    searchListView.setAdapter(adapter);
  }

  private void initSearch() {
    EditText searchEditText = findViewById(R.id.searchEditText);
    searchEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

      @Override
      public void afterTextChanged(Editable s) {
        if (!s.toString().isEmpty()) searchTextDidChanged(s.toString());
      }
    });
  }

  private void searchTextDidChanged(String text) {
    ProductRegister register = RegisterController.getInstance().getProductRegister();
    register.searchProduct(text, this::setResult);
  }

  private void setResult(List<Product> list) {
    runOnUiThread(() -> {
      products.clear();
      for (Product product : list)
        this.products.add(new ProductOrder(product, 1, ProductOrderStatus.NEW));
      this.adapter.notifyDataSetChanged();
    });
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
}