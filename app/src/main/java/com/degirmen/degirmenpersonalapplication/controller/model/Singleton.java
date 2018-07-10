package com.degirmen.degirmenpersonalapplication.controller.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Singleton {

  private List<ProductOrder> notes;
  private User user;

  private Singleton() {
    this.notes = new ArrayList<>();
  }

  public static class SingletonHolder {
    public static final Singleton HOLDER_INSTANCE = new Singleton();
  }

  public static Singleton getInstance() {
    return SingletonHolder.HOLDER_INSTANCE;
  }

  public static void addProduct(ProductOrder product) {
    Singleton.getInstance().notes.add(product);
  }

  public static List<ProductOrder> getAll() {
    return Singleton.getInstance().notes;
  }

  public int contains(ProductOrder product) {
    for (int i = 0; i < getInstance().notes.size(); i++) {
      boolean isContain = isEqual(product.product, getInstance().notes.get(i).product);
      Log.d(Singleton.class.getName(), "contains: " + isContain);
      if (isContain) {
        Log.d(Singleton.class.getName(), "contains: cool");
        return i;
      }
    }
    return -1;
  }

  public void saveUser(User user) {
    getInstance().user = user;
  }

  public User getUser() {
    return user;
  }

  public void removeAt(int position) {
    if (position < getInstance().notes.size()) {
      getInstance().notes.remove(position);
    }
  }

  public void removeProduct(ProductOrder product) {
    for (int i = 0; i < getInstance().notes.size(); i++) {
      if (isEqual(product.product, getInstance().notes.get(i).product)) {
        getInstance().notes.remove(i);
      }
    }
  }

  private boolean isEqual(Product p1, Product p2) {
    return p1.name.equals(p2.name) && Objects.equals(p1.id, p2.id) && Objects.equals(p1.price, p2.price) && Objects.equals(p1.parent, p2.parent);
  }

}