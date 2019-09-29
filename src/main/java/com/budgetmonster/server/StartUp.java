package com.budgetmonster.server;

import com.budgetmonster.utils.ConfigProperties;

public class StartUp {

  public static void main(String... args) {
    ConfigProperties.set(args);
  }
}
