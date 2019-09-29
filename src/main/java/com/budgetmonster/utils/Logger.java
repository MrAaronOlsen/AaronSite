package com.budgetmonster.utils;

public class Logger {
  public enum Color {
    ANSI_RESET("\u001B[0m"),
    ANSI_BLACK("\u001B[30m"),
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_CYAN("\u001B[36m"),
    ANSI_WHITE("\u001B[37m");

    private String code;

    Color(String code) {
      this.code = code;
    }
  }

  public static void out(String message) {
    System.out.println(message);
  }

  public static void out(String message, Color color) {
    System.out.println(color.code + message + Color.ANSI_RESET.code);
  }

  public static void err(String message) {
    System.out.println(Color.ANSI_RED.code + message + Color.ANSI_RESET.code);
  }

  public static void warn(String message) {
    System.out.println(Color.ANSI_YELLOW.code + message + Color.ANSI_RESET.code);
  }

  public static void ok(String message) {
    System.out.println(Color.ANSI_GREEN.code + message + Color.ANSI_RESET.code);
  }
}
