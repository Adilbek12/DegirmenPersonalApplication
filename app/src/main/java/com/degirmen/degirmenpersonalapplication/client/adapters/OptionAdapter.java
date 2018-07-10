package com.degirmen.degirmenpersonalapplication.client.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.snappingstepper.SnappingStepper;
import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;

import java.util.List;

public class OptionAdapter extends ArrayAdapter<ProductOrder> {
  private static final String TAG = "OptionAdapter";
  private List<ProductOrder> products;
  private Context context;

  public OptionAdapter(Context context, List<ProductOrder> products) {
    super(context, 0, products);
    this.products = products;
    this.context = context;
  }


  @Override
  public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.item_option, parent, false);
    }
    final TextView nameTV = convertView.findViewById(R.id.tvName);
    TextView tvPrice = convertView.findViewById(R.id.tvPrice);
    final SnappingStepper stepper = convertView.findViewById(R.id.stepper);
    final ImageButton buttonAdd = convertView.findViewById(R.id.buttonAdd);
    stepper.setValue(1);
    ProductOrder productOrder = products.get(position);

    nameTV.setText(productOrder.product.name);
    tvPrice.setText(String.format("%d тг", productOrder.product.price));

    Log.d(TAG, "getView: 1");
    if (Singleton.getInstance().contains(productOrder) != -1) {
      Log.d(TAG, "getView: 2");
      buttonAdd.setImageResource(R.drawable.ic_remove);
      buttonAdd.setTag("remove");
    }
    Log.d(TAG, "getView: 3");

    buttonAdd.setOnClickListener(view -> {
      if (buttonAdd.getTag().equals("remove")) {
        buttonAdd.setImageResource(R.drawable.ic_add);
        Singleton.getInstance().removeProduct(productOrder);
        buttonAdd.setTag("add");
      } else {
        if (stepper.getValue() > 0) {
          buttonAdd.setImageResource(R.drawable.ic_remove);
          ProductOrder toSingltone = new ProductOrder(productOrder.product, stepper.getValue());
          buttonAdd.setTag("remove");
          Singleton.addProduct(toSingltone);
          Toast.makeText(context, "Вы добавили " + productOrder.product.name, Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(context, "Количество - 0", Toast.LENGTH_SHORT).show();
        }
      }
    });

    return convertView;
  }
}