package com.aaronsite.server;

import com.aaronsite.utils.system.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartUp {

  public static void main(String... args) {
    SpringApplication.run(StartUp.class, args);
    ConfigProperties.set(args);
  }
}
