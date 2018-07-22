package com.degirmen.degirmenpersonalapplication.client.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.snappingstepper.SnappingStepper;
import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.client.adapters.OrderAdapter;
import com.degirmen.degirmenpersonalapplication.controller.controller.RegisterController;
import com.degirmen.degirmenpersonalapplication.controller.model.Order;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;

public class OrderActivity extends AppCompatActivity implements OrderAdapter.OnRemoveListener {

  private String titleString;
  private OrderAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order);

    titleString = getIntent().getStringExtra("title");

    initOrderTv();
    initToolbar();
    initListView();
  }

  private void initOrderTv() {
    TextView orderTV = findViewById(R.id.orderTV);
    orderTV.setOnClickListener(view -> showAlert());
  }

  private void showAlert() {
    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
    String title = titleString == null ? "Подтвердить заказ?" : "Подтвердить заказ (#" + titleString + ")?";

    builder.setTitle(title)
      .setPositiveButton("Да", (dialog, whichButton) -> alertOnClickPositiveButton(dialog))
      .setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());
    builder.show();
  }

  private void alertOnClickPositiveButton(DialogInterface dialog) {
    String table = Singleton.getInstance().getTable().title;

    Order order = new Order(Singleton.getInstance().getAll(), Singleton.getInstance().getTable().title);
    RegisterController.getInstance().getOrderRegister().toOrder(order, Singleton.getInstance().getUser(), table, success -> {
      finish();
      Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
      Singleton.getInstance().getAll().clear();
      startActivity(intent);
    });
    dialog.cancel();
  }

  private void initListView() {
    ListView listView = findViewById(R.id.orderListView);

    adapter = new OrderAdapter(this, Singleton.getInstance().getAll());
    adapter.onRemoveListener = this;
    listView.setAdapter(adapter);

    listView.setOnItemClickListener(listOnItemClickListener());

    listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
      showCommentAlert(i);
      return true;
    });
  }

  private AdapterView.OnItemClickListener listOnItemClickListener() {
    return (adapterView, view, i, l) -> {
      SnappingStepper stepper = view.findViewById(R.id.stepper);
      int value = stepper.getValue() + 1;
      stepper.setValue(value);
      Singleton.getInstance().getAll().get(i).count = value;
    };
  }

  private void initToolbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setDisplayHomeAsUpEnabled();
    toolbar.setNavigationOnClickListener(v -> finish());
  }

  private void setDisplayHomeAsUpEnabled() {
    try {
      if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    } catch (NullPointerException e) {
      Log.e(OrderActivity.class.getName(), e.getMessage());
    }
  }

  private void showCommentAlert(int position) {
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    LayoutInflater inflater = this.getLayoutInflater();

    @SuppressLint("InflateParams")
    View dialogView = inflater.inflate(R.layout.alert_comment, null);

    dialogBuilder.setView(dialogView);

    AlertDialog alertDialog = buildDialogView(dialogView, position, dialogBuilder);
    alertDialog.show();
  }

  private AlertDialog buildDialogView(View dialogView, Integer position, AlertDialog.Builder dialogBuilder) {
    EditText commentEditText = dialogView.findViewById(R.id.commentEditText);

    Button okButton = dialogView.findViewById(R.id.okButton);
    Button cancelButton = dialogView.findViewById(R.id.cancelButton);

    String comment = Singleton.getInstance().getAll().get(position).comment;

    if (comment != null && !comment.isEmpty()) {
      commentEditText.setText(comment);
      commentEditText.setSelection(commentEditText.getText().length());
    }

    AlertDialog alertDialog = dialogBuilder.create();

    okButton.setOnClickListener(v -> {
      if (!commentEditText.getText().toString().isEmpty()) {
        Singleton.getInstance().getAll().get(position).comment = commentEditText.getText().toString();
        adapter.notifyDataSetChanged();
        alertDialog.dismiss();
      }
    });

    cancelButton.setOnClickListener(view -> alertDialog.cancel());
    return alertDialog;
  }


  @Override
  public void removeAt(int position) {
    Singleton.getInstance().removeAt(position);
    adapter.notifyDataSetChanged();
  }
}