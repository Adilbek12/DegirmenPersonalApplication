package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.support.annotation.NonNull;

import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;
import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.model.TableCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.TableStatus;
import com.degirmen.degirmenpersonalapplication.controller.register.TableRegister;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonService;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonServiceGenerator;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class TableRegisterImpl implements TableRegister {

  @Override
  public void getTableList(Callback<List<Table>> callback) {
    getTableCopyList(tablesCopy -> {
      List<Table> tables = new ArrayList<>();
      for (TableCopy table : tablesCopy) tables.add(fromCopy(table));
      callback.doSomething(tables);
    });
  }

  private void getTableCopyList(Callback<List<TableCopy>> callback) {
    JsonService tableService = JsonServiceGenerator.createService(JsonService.class);
    Call<List<TableCopy>> tableCall = tableService.getTableCopyListCall("tables", Singleton.getInstance().counter++);
    tableCall.enqueue(new retrofit2.Callback<List<TableCopy>>() {
      @Override
      public void onResponse(@NonNull Call<List<TableCopy>> call, @NonNull Response<List<TableCopy>> response) {
        callback.doSomething(response.body());
        tableCall.cancel();
      }

      @Override
      public void onFailure(@NonNull Call<List<TableCopy>> call, @NonNull Throwable throwable) {
        callback.doSomething(new ArrayList<>());
        tableCall.cancel();
      }
    });
  }

  private Table fromCopy(TableCopy tableCopy) {
    Table table = new Table();
    String tableID = tableCopy.tableID == null ? "" : tableCopy.tableID;
    table.status = TableStatus.FREE;
    if (!tableID.isEmpty()) {
      table.id = tableCopy.tableID;
      if (Objects.equals(Singleton.getInstance().getUser().id, Integer.parseInt(tableCopy.userID)))
        table.status = TableStatus.MY;
      else table.status = TableStatus.FOREIGN;
    }
    table.title = tableCopy.name;
    return table;
  }
}
