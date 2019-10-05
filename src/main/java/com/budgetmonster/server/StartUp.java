package com.budgetmonster.server;

import com.budgetmonster.utils.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartUp {

  public static void main(String... args) {
    SpringApplication.run(StartUp.class, args);
    ConfigProperties.set(args);
  }
}
