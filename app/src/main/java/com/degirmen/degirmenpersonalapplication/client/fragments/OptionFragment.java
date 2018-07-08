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
import com.degirmen.degirmenpersonalapplication.controller.model.Product;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;
import com.degirmen.degirmenpersonalapplication.db.register_impl.ProductRegisterImpl;

import java.util.ArrayList;
import java.util.List;

public class OptionFragment extends Fragment {
  private static final String TAG = "OptionFragment";
  private Integer categoryId;

  public OptionFragment() {
  }

  private List<Product> products;
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
      initAll();
    }
    catch (NullPointerException e){
      Log.e(TAG, "onActivityCreated: ", e);
    }
    Log.d(TAG, "onActivityCreated: id = " + categoryId);
  }

  private void initAll() {
    ListView optionListView = view.findViewById(R.id.optionListView);
    fillArray();
    OptionAdapter adapter = new OptionAdapter(getActivity(), products);
    optionListView.setAdapter(adapter);
  }

  private void fillArray() {
    ProductRegister register = new ProductRegisterImpl();
    products = new ArrayList<>();
    products = register.getProductList(categoryId);
  }

  public void setID(int id) {
    Log.d(TAG, "setID: ");
    categoryId = id;
    initAll();
  }
}
