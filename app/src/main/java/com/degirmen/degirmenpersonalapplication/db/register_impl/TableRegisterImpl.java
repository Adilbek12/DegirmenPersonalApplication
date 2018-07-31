package com.degirmen.degirmenpersonalapplication.db.register_impl;

import android.util.Log;

import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.register.Register;
import com.degirmen.degirmenpersonalapplication.controller.register.TableRegister;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;
import com.degirmen.degirmenpersonalapplication.db.dao.TableDao;

import java.sql.SQLException;
import java.util.List;

public class TableRegisterImpl extends Register implements TableRegister {

  private TableDao tableDao;

  public TableRegisterImpl() {
    tableDao = new TableDao();
  }

  @Override
  public void getTableList(Callback<List<Table>> callback) {
    async(() -> {
      try {
        callback.doSomething(tableDao.getTableList());
      } catch (SQLException e) {
        Log.e(TableRegisterImpl.class.getName(), e.getLocalizedMessage());
      }
    });
  }
}
