package com.degirmen.degirmenpersonalapplication.controller.register;

public abstract class Register {
  public void start(Runnable runnable) {
    new Thread(runnable).start();
  }
}
