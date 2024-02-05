package com.example.easydrug.Utils;

public class FinishActivityEvent {
 public static int DRUGLIST = 0;
 public static int HOME = 1;
 public static int SPEECH = 2;

 public int scene;

 public FinishActivityEvent(int scene) {
  this.scene = scene;
 }
}