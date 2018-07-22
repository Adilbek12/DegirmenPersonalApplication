package com.degirmen.degirmenpersonalapplication.client.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.degirmen.degirmenpersonalapplication.R;
import com.degirmen.degirmenpersonalapplication.controller.model.Table;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.Holder> {

  public interface TableOnClickListener {
    void tableOnClick(int index);
  }

  public TableOnClickListener tableOnClickListener;
  private Context context;
  private List<Table> tables;

  public TableAdapter(Context context, List<Table> tables) {
    this.context = context;
    this.tables = tables;
  }

  class Holder extends RecyclerView.ViewHolder {
    Button tableButton;

    Holder(@NonNull View view) {
      super(view);
      tableButton = view.findViewById(R.id.table_button);
    }
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_table, parent, false);
    return new Holder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int i) {
    holder.tableButton.setText(tables.get(i).title);
    switch (tables.get(i).status) {
      case MY:
        holder.tableButton.setBackgroundResource(R.drawable.my_table_background);

        break;
      case FOREIGN:
        holder.tableButton.setBackgroundResource(R.drawable.foreign_table_background);

        break;
      case FREE:
        holder.tableButton.setBackgroundResource(R.drawable.free_table_background);

    }
    holder.tableButton.setOnClickListener(view -> tableOnClickListener.tableOnClick(i));
  }

  @Override
  public int getItemCount() {
    return tables.size();
  }


}
