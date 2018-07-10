package com.degirmen.degirmenpersonalapplication.controller.register;

public abstract class Register {
  public void async(Runnable runnable) {
    new Thread(runnable).run();
  }
}