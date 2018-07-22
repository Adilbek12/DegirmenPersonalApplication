package com.degirmen.degirmenpersonalapplication.client.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.degirmen.degirmenpersonalapplication.client.fragments.MenuFragment;

import java.util.ArrayList;


public class TabPagerAdapter extends FragmentStatePagerAdapter {

  private int numberOfTabs;
  private ArrayList<MenuFragment> fragments = new ArrayList<>();

  public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
    super(fm);

    this.numberOfTabs = numberOfTabs;
    for (int i = 0; i < numberOfTabs; i++) fragments.add(new MenuFragment(i));
  }

  @Override
  public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override
  public int getCount() {
    return numberOfTabs;
  }
}
