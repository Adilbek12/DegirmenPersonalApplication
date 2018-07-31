package com.degirmen.degirmenpersonalapplication.client.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigkoo.snappingstepper.SnappingStepper;
import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrderStatus;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;

import java.util.List;


public class OrderAdapter extends ArrayAdapter<ProductOrder> {


  public interface OnRemoveListener {
    void removeAt(int position);
  }

  public OnRemoveListener onRemoveListener;

  private List<ProductOrder> products;
  private Context context;

  public OrderAdapter(Context context, List<ProductOrder> products) {
    super(context, 0, products);
    this.products = products;
    this.context = context;
  }

  @NonNull
  @Override
  public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
    ProductOrder productOrder = products.get(position);
    if (productOrder.status == ProductOrderStatus.NEW) {
      convertView = LayoutInflater.from(context).inflate(R.layout.item_option, parent, false);
      initNewProductOrderView(convertView, productOrder, position);
    } else {
      convertView = LayoutInflater.from(context).inflate(R.layout.item_option_not_editable, parent, false);
      initOldProductOrderView(convertView, productOrder);
    }
    return convertView;
  }

  private void initOldProductOrderView(View convertView, ProductOrder productOrder) {
    final TextView nameTextView = convertView.findViewById(R.id.tvName);
    final TextView commentTextView = convertView.findViewById(R.id.tvComment);
    final TextView count = convertView.findViewById(R.id.tvCount);

    nameTextView.setText(productOrder.product.name);
    if (productOrder.comment != null && !productOrder.comment.isEmpty()) {
      commentTextView.setVisibility(View.VISIBLE);
      commentTextView.setText(productOrder.comment);
    }
    count.setText(String.valueOf(productOrder.count));
  }

  private void initNewProductOrderView(View convertView, ProductOrder productOrder, Integer position) {
    final TextView nameTextView = convertView.findViewById(R.id.tvName);
    final TextView priceTextView = convertView.findViewById(R.id.tvPrice);
    final TextView commentTextView = convertView.findViewById(R.id.tvComment);
    final SnappingStepper snappingStepper = convertView.findViewById(R.id.stepper);
    final ImageButton addButton = convertView.findViewById(R.id.buttonAdd);

    snappingStepper.setOnValueChangeListener((view, value) -> {
      Singleton.getInstance().getAll().get(position).count = value;
      if (value <= 0) onRemoveListener.removeAt(position);
    });


    if (productOrder.comment != null && !productOrder.comment.isEmpty()) {
      commentTextView.setVisibility(View.VISIBLE);
      commentTextView.setText(productOrder.comment);
    } else commentTextView.setVisibility(View.GONE);

    nameTextView.setText(productOrder.product.name);
    priceTextView.setText(getPrice(productOrder.product.price));

    snappingStepper.setValue(productOrder.count);

    addButton.setImageResource(R.drawable.ic_remove);
    addButton.setOnClickListener(view -> onRemoveListener.removeAt(position));

  }

  private String getPrice(Integer price) {
    return price + " тг";
  }
}
