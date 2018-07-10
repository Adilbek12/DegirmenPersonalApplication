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
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<ProductOrder> {

  public interface OnRemoveListener{
    void removeAt(int position);
  }

  public OnRemoveListener onRemoveListener;

  private static final String TAG = "OptionAdapter";
  private List<ProductOrder> products;
  private Context context;

  public OrderAdapter(Context context, List<ProductOrder> products) {
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

    ProductOrder productOrder = products.get(position);

    buttonAdd.setImageResource(R.drawable.ic_remove);
    nameTV.setText(productOrder.product.name);
    tvPrice.setText(String.format("%d тг", productOrder.product.price));
    stepper.setValue(productOrder.count);

    buttonAdd.setOnClickListener(view -> {
      onRemoveListener.removeAt(position);
    });

    return convertView;
  }
}
