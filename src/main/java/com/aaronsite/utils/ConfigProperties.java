package com.aaronsite.utils;

import com.aaronsite.utils.constants.ConfigArgs;
import com.aaronsite.utils.enums.ConfigArg;
import com.aaronsite.utils.io.Logger;

import java.util.EnumMap;
import java.util.StringTokenizer;

public class ConfigProperties {
  private static EnumMap<ConfigArg, String> argMap = new EnumMap<>(ConfigArg.class);

  public static void set(String... args) {
    if (!argMap.isEmpty()) {
      return;
    }

    for (String arg : args) {
      StringTokenizer parts = new StringTokenizer(arg);

      String argKey = parts.nextToken("=");

      ConfigArg configArg = ConfigArg.get(argKey);
      if (configArg == ConfigArg.UNKNOWN) {
        Logger.warn(String.format("Skipping unknown config argument %s.", argKey));
      } else {
        String argValue;

        if (configArg.isSystem()) {
          argValue = System.getenv(argKey);
        } else if (parts.hasMoreTokens()){
          argValue = parts.nextToken();
        } else {
          Logger.warn(String.format("Skipping config argument with no value %s.", argKey));
          return;
        }

        add(configArg, argValue);
        Logger.out(String.format("Loading Env Arg Key: %s - Value: %s", argKey, configArg.print(argValue)));
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