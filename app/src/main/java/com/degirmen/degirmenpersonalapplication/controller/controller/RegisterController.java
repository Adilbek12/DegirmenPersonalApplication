package com.degirmen.degirmenpersonalapplication.controller.controller;

import com.degirmen.degirmenpersonalapplication.controller.register.AuthRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.OrderRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.TableRegister;
import com.degirmen.degirmenpersonalapplication.db.register_impl.AuthRegisterImpl;
import com.degirmen.degirmenpersonalapplication.db.register_impl.OrderRegisterImpl;
import com.degirmen.degirmenpersonalapplication.db.register_impl.ProductRegisterImpl;
import com.degirmen.degirmenpersonalapplication.db.register_impl.TableRegisterImpl;
import com.degirmen.degirmenpersonalapplication.stand.register_impl.AuthRegisterStand;
import com.degirmen.degirmenpersonalapplication.stand.register_impl.OrderRegisterStand;
import com.degirmen.degirmenpersonalapplication.stand.register_impl.ProductRegisterStand;
import com.degirmen.degirmenpersonalapplication.stand.register_impl.TableRegisterStand;

public class RegisterController {

  private static final boolean START_IMPL = false;

  private AuthRegister authRegister;
  private OrderRegister orderRegister;
  private ProductRegister productRegister;
  private TableRegister tableRegister;

  RegisterController() {
    if (START_IMPL) {
      initImplRegisters();
    } else {
      initStandRegisters();
    }
  }

  private void initImplRegisters() {
    authRegister = new AuthRegisterImpl();
    orderRegister = new OrderRegisterImpl();
    productRegister = new ProductRegisterImpl();
    tableRegister = new TableRegisterImpl();
  }

  private void initStandRegisters() {
    authRegister = new AuthRegisterStand();
    orderRegister = new OrderRegisterStand();
    productRegister = new ProductRegisterStand();
    tableRegister = new TableRegisterStand();
  }

  public static class RegisterControllerHolder {
    static final RegisterController HOLDER_INSTANCE = new RegisterController();
  }

  public static RegisterController getInstance() {
    return RegisterControllerHolder.HOLDER_INSTANCE;
  }

  public AuthRegister getAuthRegister() {
    return authRegister;
  }

  public OrderRegister getOrderRegister() {
    return orderRegister;
  }

  public ProductRegister getProductRegister() {
    return productRegister;
  }

  public TableRegister getTableRegister() {
    return tableRegister;
  }
}
