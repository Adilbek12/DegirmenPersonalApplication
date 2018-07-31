package com.degirmen.degirmenpersonalapplication.controller.model;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Singleton implements Serializable {
  private List<ProductOrder> notes;
  private User user;
  private Context context;
  private Table table;
  private Integer zakazId;

  public Table getTable() {
    return table;
  }

  public void saveTable(Table table) {
    this.table = table;
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
      boolean isContain = isEqual(product.product, getAll().get(i).product);
      if (isContain && getAll().get(i).status != ProductOrderStatus.OLD) {
        return i;
      }
    }
    return -1;
  }

  public void saveZakazId(Integer zakazId) {
    this.zakazId = zakazId;
  }

  public Integer getZakazId() {
    return zakazId;
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


//  public static class SingletonHolder {
//    public static final Singleton HOLDER_INSTANCE = new Singleton();
//  }
//
//  public static Singleton getInstance() {
//    return SingletonHolder.HOLDER_INSTANCE;
//  }


  private static volatile Singleton sSoleInstance;

  //private constructor.
  private Singleton() {
    this.notes = new ArrayList<>();
    //Prevent form the reflection api.
    if (sSoleInstance != null) {
      throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
    }
  }

  public static Singleton getInstance() {
    if (sSoleInstance == null) { //if there is no instance available... create new one
      synchronized (Singleton.class) {
        if (sSoleInstance == null) sSoleInstance = new Singleton();
      }
    }

    return sSoleInstance;
  }

  //Make singleton from serialize and deserialize operation.
  protected Singleton readResolve() {
    return getInstance();
  }
}