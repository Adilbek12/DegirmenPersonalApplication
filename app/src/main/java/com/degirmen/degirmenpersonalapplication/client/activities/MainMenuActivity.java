package com.degirmen.degirmenpersonalapplication.client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.client.adapters.TableAdapter;
import com.degirmen.degirmenpersonalapplication.controller.controller.RegisterController;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;
import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.model.TableStatus;
import com.degirmen.degirmenpersonalapplication.controller.register.OrderRegister;

import java.util.List;

public class MainMenuActivity extends AppCompatActivity implements TableAdapter.TableOnClickListener {

  private RecyclerView tableRecyclerView;
  private TableAdapter adapter;
  private List<Table> tableList;

  private ProgressBar progressBar;
  private View darkView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_menu);


    darkView = findViewById(R.id.darkView);
    progressBar = findViewById(R.id.progressBar);

    initRecyclerView();
    initReloadImageView();

    setTitle(Singleton.getInstance().getUser().name);
  }

  private void initReloadImageView() {
    ImageView reloadImage = findViewById(R.id.reloadImage);
    reloadImage.setOnClickListener(view -> reloadAdapter());
  }

  private void reloadAdapter() {
    RegisterController.getInstance().getTableRegister().getTableList(tableList -> {
      runOnUiThread(() -> {
        this.tableList = tableList;
        adapter.notifyDataSetChanged();
      });
    });
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    reloadAdapter();
  }

  @Override
  public void tableOnClick(int index) {
    if (tableList.get(index).status == TableStatus.MY || tableList.get(index).status == TableStatus.FREE) {
      if (tableList.get(index).status == TableStatus.MY) {
        OrderRegister register = RegisterController.getInstance().getOrderRegister();
        register.getOrders(Singleton.getInstance().getUser(), Singleton.getInstance().getTable(), productOrders -> Singleton.getInstance().addAllProduct(productOrders));
      }
      Singleton.getInstance().saveTable(tableList.get(index));
      Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
      intent.putExtra("title", tableList.get(index).title);
      startActivity(intent);
    }
  }

  private void loading(boolean l) {
    darkView.setVisibility(l ? View.VISIBLE : View.GONE);
    progressBar.setVisibility(l ? View.VISIBLE : View.GONE);
  }

  private void initRecyclerView() {
    loading(true);
    RegisterController.getInstance().getTableRegister().getTableList(tableList -> runOnUiThread(() -> {
      loading(false);
      this.tableList = tableList;
      adapter = new TableAdapter(this, this.tableList);
      adapter.tableOnClickListener = this;
      tableRecyclerView = findViewById(R.id.tableRecyclerView);
      RecyclerView.LayoutManager manager = new GridLayoutManager(this, 4);
      tableRecyclerView.setAdapter(adapter);
      tableRecyclerView.setLayoutManager(manager);
    }));
  }
}
