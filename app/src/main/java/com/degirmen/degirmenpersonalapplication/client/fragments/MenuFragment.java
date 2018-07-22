package com.degirmen.degirmenpersonalapplication.client.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.client.activities.OptionActivity;
import com.degirmen.degirmenpersonalapplication.client.adapters.MenuAdapter;
import com.degirmen.degirmenpersonalapplication.client.utils.Common;
import com.degirmen.degirmenpersonalapplication.controller.controller.RegisterController;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductType;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MenuFragment extends Fragment {

  private int position;

  public MenuFragment() {}

  @SuppressLint("ValidFragment")
  public MenuFragment(int position) {
    this.position = position;
  }

  private View view;

  private String titleString;

  private ListView menuListView;
  private List<ProductCategory> productCategoryList = new ArrayList<>();

  private ProgressBar progressBar;
  private View darkView;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_menu, container, false);
    darkView = view.findViewById(R.id.darkView);
    progressBar = view.findViewById(R.id.progressBar);
    initAll();
    return view;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (getActivity() != null) titleString = getActivity().getIntent().getStringExtra("title");
    menuListView.setOnItemClickListener((adapterView, view, index, l) -> {
      if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && !Common.isTablet(getActivity()))
        startActivity(getIntentWithExtra(index));
      else
        reloadDbWith(productCategoryList.get(index).id);
    });
  }

  private void initAll() {
    menuListView = view.findViewById(R.id.menuListView);
    ProductRegister register = RegisterController.getInstance().getProductRegister();
    loading(true);
    register.getProductCategory(getProductType().get(position), this::setCategoryList);
  }

  private void setCategoryList(List<ProductCategory> productCategoryList) {
    if (getActivity() != null)
      getActivity().runOnUiThread(() -> {
        loading(false);
        this.productCategoryList = productCategoryList;
        MenuAdapter adapter = new MenuAdapter(getActivity(), productCategoryList);
        menuListView.setAdapter(adapter);
      });
    else loading(false);
  }

  private List<ProductType> getProductType() {
    List<ProductType> generalCategories = new ArrayList<>();
    generalCategories.add(new ProductType(1));
    generalCategories.add(new ProductType(2));
    generalCategories.add(new ProductType(3));
    generalCategories.add(new ProductType(5));
    generalCategories.add(new ProductType(6));
    generalCategories.add(new ProductType(7));
    return generalCategories;
  }

  private void reloadDbWith(int id) {
    FragmentManager fragmentManager = getFragmentManager();
    if (fragmentManager != null) {
      OptionFragment fragment = (OptionFragment) getFragmentManager().findFragmentById(R.id.optionFragment);
      Objects.requireNonNull(fragment).setId(id);
    }
  }

  private void loading(boolean l) {
    darkView.setVisibility(l ? View.VISIBLE : View.GONE);
    progressBar.setVisibility(l ? View.VISIBLE : View.GONE);
  }

  public Intent getIntentWithExtra(Integer index) {
    Intent intent = new Intent(getActivity(), OptionActivity.class);
    intent.putExtra("id", productCategoryList.get(index).id);
    if (titleString != null) intent.putExtra("title", titleString);
    return intent;
  }
}
