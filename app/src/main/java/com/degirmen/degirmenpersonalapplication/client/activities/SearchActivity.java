package com.degirmen.degirmenpersonalapplication.client.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.client.adapters.OptionAdapter;
import com.degirmen.degirmenpersonalapplication.controller.controller.RegisterController;
import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrderStatus;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    searchListView.setOnItemLongClickListener(searchListOnItemLongClickListener());
  }

  private AdapterView.OnItemLongClickListener searchListOnItemLongClickListener() {
    return (adapterView, view, i, l) -> {
      int index = Singleton.getInstance().contains(products.get(i));
      if (index != -1) {
        showCommentAlert(index);
      }
      return true;
    };
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

  private void showCommentAlert(int position) {
    LayoutInflater inflater = this.getLayoutInflater();
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(this));

    @SuppressLint("InflateParams")
    View dialogView = inflater.inflate(R.layout.alert_comment, null);
    dialogBuilder.setView(dialogView);

    AlertDialog alertDialog = buildDialogView(dialogView, dialogBuilder, position);
    alertDialog.show();
  }

  private AlertDialog buildDialogView(View dialogView, AlertDialog.Builder dialogBuilder, Integer position) {
    EditText commentEditText = dialogView.findViewById(R.id.commentEditText);
    Button okButton = dialogView.findViewById(R.id.okButton);
    Button cancelButton = dialogView.findViewById(R.id.cancelButton);

    String comment = Singleton.getInstance().getAll().get(position).comment;

    if (comment != null) {
      commentEditText.setText(comment);
      commentEditText.setSelection(commentEditText.getText().length());
    }

    AlertDialog alertDialog = dialogBuilder.create();

    okButton.setOnClickListener(view -> {
      if (!commentEditText.getText().toString().isEmpty()) {
        Singleton.getInstance().getAll().get(position).comment = commentEditText.getText().toString();
        adapter.notifyDataSetChanged();
        alertDialog.dismiss();
      }
    });

    cancelButton.setOnClickListener(view -> alertDialog.cancel());
    return alertDialog;
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