package com.aaronsite.server;

import com.aaronsite.utils.io.Logger;
import com.aaronsite.utils.system.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartUp {

  public static void main(String... args) {
    SpringApplication.run(StartUp.class, args);

    Logger.line();
    Logger.ok("----------------------------------------");
    Logger.ok("--- Setting Args");
    Logger.ok("----------------------------------------");
    Logger.line();

    ConfigProperties.set(args);

    Logger.line();
    Logger.ok("----------------------------------------");
    Logger.ok("--- Start Up Complete.");
    Logger.ok("----------------------------------------");
  }
}
