package com.degirmen.degirmenpersonalapplication.client.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.client.activities.OptionActivity;
import com.degirmen.degirmenpersonalapplication.client.adapters.MenuAdapter;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductType;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;
import com.degirmen.degirmenpersonalapplication.db.register_impl.ProductRegisterImpl;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {
  private static final String TAG = "MenuFragment";

  private int position;

  @SuppressLint("ValidFragment")
  public MenuFragment(int position) {
    this.position = position;
  }

  public MenuFragment() {
  }

  public interface OnFragmentInteractionListener {
    void fragmentChanged(int position);
  }


  private ListView menuListView;
  private MenuAdapter adapter;
  private List<ProductCategory> categories;
  private View view;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_menu, container, false);
    initAll();

    return view;
  }

  private void initAll() {
    menuListView = view.findViewById(R.id.menuListView);
    fillArray();
    adapter = new MenuAdapter(getActivity(), categories);
    menuListView.setAdapter(adapter);
  }

  private void fillArray() {
    categories = new ArrayList<>();
    ProductRegister stand = new ProductRegisterImpl();
    List<ProductType> types = getProductTypeList();
    categories = stand.getProductCategory(types.get(position));
  }

  private List<ProductType> getProductTypeList() {
    List<ProductType> generalCategories = new ArrayList<>();
    generalCategories.add(new ProductType(1));
    generalCategories.add(new ProductType(2));
    generalCategories.add(new ProductType(3));
    generalCategories.add(new ProductType(5));
    generalCategories.add(new ProductType(6));
    generalCategories.add(new ProductType(7));
    return generalCategories;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    menuListView.setOnItemClickListener((adapterView, view, index, l) -> {
      if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
        Intent intent = new Intent(getActivity(), OptionActivity.class);
        intent.putExtra("id", categories.get(index).id);
        startActivity(intent);
      } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        reloadDBWith(categories.get(index).id);
      }
    });
  }

  private void reloadDBWith(int id) {
    OptionFragment fragment = (OptionFragment) getFragmentManager().findFragmentById(R.id.optionFragment);
    Log.d(TAG, "reloadDBWith: id = " + id);
    fragment.setID(id);
  }

}
