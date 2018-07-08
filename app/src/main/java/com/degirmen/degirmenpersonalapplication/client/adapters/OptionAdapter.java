package com.degirmen.degirmenpersonalapplication.client.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigkoo.snappingstepper.SnappingStepper;
import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.controller.model.NoteSingleton;
import com.degirmen.degirmenpersonalapplication.controller.model.Product;

import java.util.List;

public class OptionAdapter extends ArrayAdapter<Product> {
  private static final String TAG = "OptionAdapter";
  private List<Product> products;
  private Context context;

  public OptionAdapter(Context context, List<Product> products) {
    super(context, 0, products);
    this.products = products;
    this.context = context;
  }


  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.item_option, parent, false);
    }
    final TextView nameTV = convertView.findViewById(R.id.tvName);
    TextView tvPrice = convertView.findViewById(R.id.tvPrice);
    final SnappingStepper stepper = convertView.findViewById(R.id.stepper);
    final ImageButton buttonAdd = convertView.findViewById(R.id.buttonAdd);

    buttonAdd.setOnClickListener(view -> {
      buttonAdd.setImageResource(R.drawable.ic_remove);
      NoteSingleton.addOrder(products.get(position).name);
    });
    nameTV.setText(products.get(position).name);
    tvPrice.setText(String.format("%d тг", products.get(position).price));

    return convertView;
  }
}
