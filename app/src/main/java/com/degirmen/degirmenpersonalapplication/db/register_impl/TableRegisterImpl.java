package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.ProductCategoryCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.Singleton;
import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.model.TableCopy;
import com.degirmen.degirmenpersonalapplication.controller.model.TableStatus;
import com.degirmen.degirmenpersonalapplication.controller.register.TableRegister;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonService;
import com.degirmen.degirmenpersonalapplication.controller.service.JsonServiceGenerator;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class TableRegisterImpl implements TableRegister {
  private static final String TAG = "TableRegisterImpl";

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
    Call<String> tableCall = tableService.getTableCopyListCall("tables");
    tableCall.enqueue(new retrofit2.Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        String responseString = response.body();
        String[] arrayString = responseString.split("/>");
        if (arrayString.length > 1){
          String jsonString = arrayString[1];
          Type listType = new TypeToken<ArrayList<TableCopy>>(){}.getType();
          List<TableCopy> categories =
            new GsonBuilder().create().fromJson(jsonString, listType);

          for(TableCopy user: categories){
            Log.d(TAG, "onResponse: " + user.toString());
          }
          callback.doSomething(categories);
        }
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {

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
