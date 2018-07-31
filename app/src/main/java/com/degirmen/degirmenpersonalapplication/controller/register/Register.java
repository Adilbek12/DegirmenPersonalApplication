package com.degirmen.degirmenpersonalapplication.controller.register;

public abstract class Register {
  public void async(Runnable runnable) {
    new Thread(runnable).start();
  }

  protected void sleep() {
//    try {
//      Thread.sleep(1000L);
//    } catch (InterruptedException e) {
//      throw new RuntimeException();
//    }
  }
}