package com.degirmen.degirmenpersonalapplication.controller.register;

import com.degirmen.degirmenpersonalapplication.controller.model.Table;
import com.degirmen.degirmenpersonalapplication.controller.util.Callback;

import java.util.List;

public interface TableRegister {
  void getTableList(Callback<List<Table>> callback);
}
