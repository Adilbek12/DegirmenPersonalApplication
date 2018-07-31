package com.degirmen.degirmenpersonalapplication.stand.register_impl;

import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.model.TableStatus;
import com.degirmen.degirmenpersonalapplication.controller.register.Register;
import com.degirmen.degirmenpersonalapplication.controller.register.TableRegister;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class TableRegisterStand extends Register implements TableRegister {
  @Override
  public void getTableList(Callback<List<Table>> callback) {
    async(() -> {
      sleep();
      callback.doSomething(getTableList());
    });
  }

  private List<Table> getTableList() {
    List<Table> tables = new ArrayList<>();
    tables.add(new Table(TableStatus.MY, "1", "Test"));
    tables.add(new Table(TableStatus.FOREIGN, "2", "Test"));
    tables.add(new Table(TableStatus.FREE, "3", "Test"));
    tables.add(new Table(TableStatus.FREE, "4", "Test"));
    tables.add(new Table(TableStatus.FREE, "5", "test"));
    tables.add(new Table(TableStatus.FREE, "6", "test"));
    tables.add(new Table(TableStatus.FOREIGN, "7", "test"));
    tables.add(new Table(TableStatus.MY, "8", "test"));
    return tables;
  }
}
