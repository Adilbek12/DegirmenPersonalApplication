package com.degirmen.degirmenpersonalapplication.client.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.client.adapters.OptionAdapter;
import com.degirmen.degirmenpersonalapplication.controller.controller.RegisterController;
import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OptionFragment extends Fragment {

  private View view;

  private Integer categoryId;
  private OptionAdapter adapter;

  private List<ProductOrder> productOrders = new ArrayList<>();

  private ProgressBar progressBar;
  private View darkView;


  public OptionFragment() {}

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_option, container, false);
    darkView = view.findViewById(R.id.darkView);
    progressBar = view.findViewById(R.id.progressBar);
    return view;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    try {
      if (getActivity() != null)
        categoryId = Objects.requireNonNull(getActivity().getIntent().getExtras()).getInt("id");
      initAll();
    } catch (NullPointerException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    adapter.notifyDataSetChanged();
  }

  private void initAll() {
    ListView optionListView = view.findViewById(R.id.optionListView);
    ProductRegister register = RegisterController.getInstance().getProductRegister();

    adapter = new OptionAdapter(getActivity(), productOrders);
    optionListView.setAdapter(adapter);

    loading(true);
    register.getProductList(categoryId, this::setProductList);
    optionListView.setOnItemLongClickListener(optionOnItemLongClickListener());

  }

  private AdapterView.OnItemLongClickListener optionOnItemLongClickListener() {
    return (adapterView, view, i, l) -> {
      int index = Singleton.getInstance().contains(productOrders.get(i));
      if (index != -1) {
        showCommentAlert(index);
      }
      return true;
    };
  }

  private void setProductList(List<Product> products) {
    if (getActivity() != null)
      getActivity().runOnUiThread(() -> {
        loading(false);
        for (Product product : products) productOrders.add(new ProductOrder(product, 1));
        adapter.notifyDataSetChanged();
      });
    else
      loading(false);
  }

  private void showCommentAlert(int position) {
    LayoutInflater inflater = this.getLayoutInflater();
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

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

  private void loading(boolean l) {
    darkView.setVisibility(l ? View.VISIBLE : View.GONE);
    progressBar.setVisibility(l ? View.VISIBLE : View.GONE);
  }

  public void setId(int id) {
    categoryId = id;
    initAll();
  }
}