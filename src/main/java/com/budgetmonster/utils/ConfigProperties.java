package com.budgetmonster.utils;

import java.util.EnumMap;

public class ConfigProperties {
  private static EnumMap<ConfigArg, String> argMap = new EnumMap<>(ConfigArg.class);

  public static void set(String... args) {
    if (!argMap.isEmpty()) {
      return;
    }

    for (String arg : args) {
      if (arg.contains("=")) {
        String[] parts = arg.split("=", 2);

        if (parts.length == 2) {
          String argKey = parts[0];
          String argValue = parts[1];

          ConfigArg configArg = ConfigArg.get(argKey);
          if (configArg == ConfigArg.UNKNOWN) {
            Logger.warn(String.format("Skipping unknown config argument %s.", argKey));
          } else {
            add(configArg, argValue);
            Logger.ok(String.format("Loaded argument %s: %s", argKey, argValue));
          }
        }
      } else {
        Logger.warn(String.format("Skipping invalid argument %s.", arg));
      }
    }
  }

  public static void add(ConfigArg arg, String value) {
    argMap.put(arg, value);
  }

  public static String getValue(ConfigArg arg) {
    return argMap.get(arg);
  }

  public static String getSysArg(ConfigArg arg) {
    return arg.getKey() + "=" + System.getProperty(arg.getKey(), ConfigArgs.UNKNOWN);
  }
}