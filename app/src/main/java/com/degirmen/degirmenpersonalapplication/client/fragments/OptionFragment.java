package com.degirmen.degirmenpersonalapplication.client.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.client.adapters.OptionAdapter;
import com.degirmen.degirmenpersonalapplication.controller.controller.RegisterController;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;

import java.util.ArrayList;
import java.util.List;

public class OptionFragment extends Fragment {
  private static final String TAG = "OptionFragment";
  private Integer categoryId;

  public OptionFragment() {
  }

  private View view;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_option, container, false);
    return view;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    try {
      categoryId = getActivity().getIntent().getExtras().getInt("id");
      Log.d(TAG, "onActivityCreated: id = " + categoryId);
      initAll();
    } catch (NullPointerException e) {
    }
  }

  private void initAll() {
    ListView optionListView = view.findViewById(R.id.optionListView);
    ProductRegister register = RegisterController.getInstance().getProductRegister();

    //FIXME: NEED INDICATOR
    register.getProductList(categoryId, products -> {
      List<ProductOrder> productOrders = new ArrayList<>();
      for (Product product : products) {
        productOrders.add(new ProductOrder(product, 1));
      }
      optionListView.setAdapter(new OptionAdapter(getActivity(), productOrders));
    });
  }

  public void setID(int id) {
    Log.d(TAG, "setID: ");
    categoryId = id;
    initAll();
  }
}