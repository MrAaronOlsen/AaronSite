package com.aaronsite.utils.enums;

import com.aaronsite.utils.constants.ConfigArgs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ConfigArg {
  DB_URL(ConfigArgs.DB_URL),
  DB_PW(ConfigArgs.DB_PW),
  DB_USER(ConfigArgs.DB_USER),
  DB_SCHEMA(ConfigArgs.DB_SCHEMA),
  UNKNOWN(ConfigArgs.UNKNOWN);

  private String key;
  private static Map<String, ConfigArg> map = new ConcurrentHashMap<>();
  static {
    for (ConfigArg value : ConfigArg.values()) {
      map.put(value.getKey(), value);
    }
  }

  ConfigArg(String key) {
    this.key = key;
  }

  public static ConfigArg get(String arg) {
    return map.getOrDefault(arg, UNKNOWN);
  }

  public String getKey() {
    return key;
  }
}