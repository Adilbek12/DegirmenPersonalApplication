package com.degirmen.degirmenpersonalapplication.controller.model;

import java.util.ArrayList;

public class NoteSingleton {

  private ArrayList<String> notes;

  private NoteSingleton(ArrayList<String> notes) {
    this.notes = notes;
  }

  private static NoteSingleton note = new NoteSingleton(new ArrayList<String>());

  public static void addOrder(String order) {
    NoteSingleton.note.notes.add(order);
  }

  public static ArrayList<String> getAll() {
    return NoteSingleton.note.notes;
  }

}