package com.degirmen.degirmenpersonalapplication.controller.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
  private static final String TAG = "Singleton";

  private List<ProductOrder> notes;
  private User user;
  private Context context;
  private Table table;

  public Integer counter = 0;

  private Singleton() {
    this.notes = new ArrayList<>();
  }

  public Table getTable() {
    return table;
  }

  public void saveTable(Table table) {
    this.table = table;
  }

  public static class SingletonHolder {
    public static final Singleton HOLDER_INSTANCE = new Singleton();
  }

  public static Singleton getInstance() {
    return SingletonHolder.HOLDER_INSTANCE;
  }

  public void addProduct(ProductOrder product) {
    Singleton.getInstance().notes.add(product);
  }

  public void addAllProduct(List<ProductOrder> productOrders) {
    notes.addAll(productOrders);
  }

  public List<ProductOrder> getAll() {
    return Singleton.getInstance().notes;
  }

  public List<ProductOrder> getAllNew() {
    List<ProductOrder> productCopyList = new ArrayList<>();
    for (ProductOrder productOrder : notes)
      if (productOrder.status == ProductOrderStatus.NEW) productCopyList.add(productOrder);
    return productCopyList;
  }

  public int contains(ProductOrder product) {
    for (int i = 0; i < getInstance().notes.size(); i++) {
      Log.d(TAG, "contains: " + product.product.id + ",   " + getAll().get(i).product);
      boolean isContain = isEqual(product.product, getAll().get(i).product);
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
    return p1.id.equals(p2.id);
  }

  public Context getContext() {
    return context;
  }

  public void saveContext(Context context) {
    this.context = context;
  }
}