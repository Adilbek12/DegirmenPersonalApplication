package com.degirmen.degirmenpersonalapplication.client.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.degirmen.degirmenpersonalapplication.client.fragments.MenuFragment;


public class TabPagerAdapter extends FragmentStatePagerAdapter {

  private int numberOfTabs;

  public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
    super(fm);
    this.numberOfTabs = numberOfTabs;
  }

  @Override
  public Fragment getItem(int position) {
    return new MenuFragment(position);
  }

  @Override
  public int getCount() {
    return numberOfTabs;
  }
}
