package com.degirmen.degirmenpersonalapplication.client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.client.adapters.TabPagerAdapter;
import com.degirmen.degirmenpersonalapplication.client.fragments.MenuFragment;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductType;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;
import com.degirmen.degirmenpersonalapplication.db.register_impl.ProductRegisterImpl;

import java.util.ArrayList;


public class MenuActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener {

  private TabLayout tabLayout;
  private ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu);

    initTabLayout();
    initImageView();
    initViewPager();
  }

  private void initViewPager() {
    viewPager = findViewById(R.id.viewPager);

    viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
  }

  private void initImageView() {
    findViewById(R.id.searchImageView).setOnClickListener(v -> intentToClass(SearchActivity.class));
    findViewById(R.id.orderImageView).setOnClickListener(v -> intentToClass(OptionActivity.class));
  }

  private void intentToClass(Class cls) {
    startActivity(new Intent(getApplicationContext(), cls));
  }

  private void initTabLayout() {
    tabLayout = findViewById(R.id.tablayout);
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    addTabs();
    tabLayout.addOnTabSelectedListener(getOnSelectedListener());
  }

  private void addTabs() {
    ProductRegister register = new ProductRegisterImpl();
    ArrayList<ProductType> types = (ArrayList<ProductType>) register.getProductTypeList();
    for (ProductType type : types) {
      tabLayout.addTab(tabLayout.newTab().setText(type.root + ""));
    }
  }

  private TabLayout.BaseOnTabSelectedListener getOnSelectedListener() {
    return new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    };
  }

  @Override
  public void fragmentChanged(int position) {
  }
}
