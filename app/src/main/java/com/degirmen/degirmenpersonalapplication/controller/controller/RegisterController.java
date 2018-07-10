package com.degirmen.degirmenpersonalapplication.controller.controller;

import com.degirmen.degirmenpersonalapplication.controller.register.AuthRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.OrderRegister;
import com.degirmen.degirmenpersonalapplication.controller.register.ProductRegister;
import com.degirmen.degirmenpersonalapplication.db.register_impl.AuthRegisterImpl;
import com.degirmen.degirmenpersonalapplication.db.register_impl.OrderRegisterImpl;
import com.degirmen.degirmenpersonalapplication.db.register_impl.ProductRegisterImpl;
import com.degirmen.degirmenpersonalapplication.stand.register_impl.AuthRegisterStand;
import com.degirmen.degirmenpersonalapplication.stand.register_impl.OrderRegisterStand;
import com.degirmen.degirmenpersonalapplication.stand.register_impl.ProductRegisterStand;

public class RegisterController {

  private AuthRegister authRegister;
  private OrderRegister orderRegister;
  private ProductRegister productRegister;

  RegisterController() {
    initImplRegisters();
  }

  private void initImplRegisters() {
    authRegister = new AuthRegisterImpl();
    orderRegister = new OrderRegisterImpl();
    productRegister = new ProductRegisterImpl();
  }

  private void initStandRegisters() {
    authRegister = new AuthRegisterStand();
    orderRegister = new OrderRegisterStand();
    productRegister = new ProductRegisterStand();
  }

  public static class RegisterControllerHolder {
    public static final RegisterController HOLDER_INSTANCE = new RegisterController();
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
}
