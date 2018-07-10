package com.degirmen.degirmenpersonalapplication.client.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.client.adapters.OrderAdapter;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;

public class OrderActivity extends AppCompatActivity implements OrderAdapter.OnRemoveListener {

  private TextView orderTV;

  @Override
  public void removeAt(int position) {
    Singleton.getInstance().removeAt(position);
    adapter.notifyDataSetChanged();

  }

  private OrderAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order);

    orderTV = findViewById(R.id.orderTV);
    orderTV.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
        alert.setTitle("Title");
        alert.setMessage("Message :");

        // Set an EditText view to get user input
        final EditText input = new EditText(getApplicationContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            String value = input.getText().toString();
            Log.d("", "Pin Value : " + value);
            return;
          }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                  public void onClick(DialogInterface dialog, int which) {

                    return;
                  }
                });
        alert.show();
      }
    });
    initToolbar();
    initListView();
  }

  private void initListView() {
    ListView listView = findViewById(R.id.orderListView);
    adapter = new OrderAdapter(this, Singleton.getAll());
    adapter.onRemoveListener = this;
    listView.setAdapter(adapter);
  }

  private void initToolbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setDisplayHomeAsUpEnabled();
    toolbar.setNavigationOnClickListener(v -> finish());
  }

  private void setDisplayHomeAsUpEnabled() {
    try {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    } catch (NullPointerException e) {
      Log.e(OrderActivity.class.getName(), e.getMessage());
    }
  }
}