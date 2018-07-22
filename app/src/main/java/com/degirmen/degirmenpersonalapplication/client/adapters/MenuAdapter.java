package com.degirmen.degirmenpersonalapplication.client.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategory;

import java.util.List;

public class MenuAdapter extends ArrayAdapter<ProductCategory> {
  private List<ProductCategory> categories;
  private Context context;

  public MenuAdapter(Context context, List<ProductCategory> categories) {
    super(context, 0, categories);
    this.categories = categories;
    this.context = context;
  }

  @NonNull
  @Override
  public View getView(int position, View convertView, @NonNull ViewGroup parent) {
    if (convertView == null)
      convertView = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);

    ProductCategory category = categories.get(position);

    TextView textView = convertView.findViewById(R.id.tvName);
    textView.setText(category.name);

    return convertView;
  }

}
