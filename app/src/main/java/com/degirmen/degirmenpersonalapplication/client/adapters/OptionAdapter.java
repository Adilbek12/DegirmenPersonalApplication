package com.degirmen.degirmenpersonalapplication.client.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.snappingstepper.SnappingStepper;
import com.bigkoo.snappingstepper.listener.SnappingStepperValueChangeListener;
import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.controller.model.ProductOrder;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;

import java.util.List;

public class OptionAdapter extends ArrayAdapter<ProductOrder> {

  private List<ProductOrder> products;
  private Context context;

  public OptionAdapter(Context context, List<ProductOrder> products) {
    super(context, 0, products);
    this.products = products;
    this.context = context;
  }

  @NonNull
  @Override
  public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
    if (convertView == null)
      convertView = LayoutInflater.from(context).inflate(R.layout.item_option, parent, false);

    final TextView nameTextView = convertView.findViewById(R.id.tvName);
    final TextView priceTextView = convertView.findViewById(R.id.tvPrice);
    final TextView commentTextView = convertView.findViewById(R.id.tvComment);
    final SnappingStepper snappingStepper = convertView.findViewById(R.id.stepper);
    final ImageButton button = convertView.findViewById(R.id.buttonAdd);

    snappingStepper.setValue(1);
    commentTextView.setVisibility(View.GONE);

    ProductOrder productOrder = products.get(position);
    nameTextView.setText(productOrder.product.name);
    priceTextView.setText(getPrice(productOrder.product.price));

    int index = Singleton.getInstance().contains(productOrder);

    if (index != -1) {
      toRemoveButton(button);

      ProductOrder fromSingleton = Singleton.getInstance().getAll().get(index);
      snappingStepper.setValue(fromSingleton.count);
      String comment = fromSingleton.comment;

      if (comment != null) {
        commentTextView.setVisibility(View.VISIBLE);
        commentTextView.setText(comment);
      }
    } else toAddButton(button);

    snappingStepper.setOnValueChangeListener(snapperOnValueChangeListener(productOrder, button));
    button.setOnClickListener(onClickAddButton(snappingStepper, productOrder));
    return convertView;
  }

  private String getPrice(Integer price) {
    return price + " тг";
  }

  private SnappingStepperValueChangeListener snapperOnValueChangeListener(ProductOrder productOrder, ImageButton button) {
    return (view, value) -> {
      int index = Singleton.getInstance().contains(productOrder);
      if (index != -1) Singleton.getInstance().getAll().get(index).count = value;
      if (value <= 0) {
        Singleton.getInstance().getAll().remove(index);
        toAddButton(button);
      }
    };
  }

  private void toAddButton(ImageButton button) {
    button.setImageResource(R.drawable.ic_add);
    button.setTag("add");
  }

  private void toRemoveButton(ImageButton button) {
    button.setImageResource(R.drawable.ic_remove);
    button.setTag("remove");
  }

  private View.OnClickListener onClickAddButton(SnappingStepper snappingStepper, ProductOrder productOrder) {
    return view -> {
      ImageButton button = (ImageButton) view;
      if (button.getTag().equals("remove")) {
        toAddButton(button);
        Singleton.getInstance().removeProduct(productOrder);
        notifyDataSetChanged();
      } else if (button.getTag().equals("add")) {
        if (snappingStepper.getValue() > 0) {
          ProductOrder toSingleton = new ProductOrder(productOrder.product, snappingStepper.getValue());
          toRemoveButton(button);
          Singleton.getInstance().addProduct(toSingleton);
          Toast.makeText(context, "Вы добавили " + productOrder.product.name, Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(context, "Невозможно добавить 0 продуктов", Toast.LENGTH_SHORT).show();
        }
      }
    };
  }
}