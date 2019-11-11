package com.aaronsite.utils.enums;

import com.aaronsite.utils.constants.ConfigArgs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ConfigArg {
  DB_URL(ConfigArgs.DB_URL),
  DB_PW(ConfigArgs.DB_PW),
  DB_USER(ConfigArgs.DB_USER),
  DB_SCHEMA(ConfigArgs.DB_SCHEMA),

  // Heroku Configs
  JDBC_DATABASE_URL(ConfigArgs.JDBC_DATABASE_URL, DB_URL),
  JDBC_DATABASE_USERNAME(ConfigArgs.JDBC_DATABASE_USERNAME, DB_USER),
  JDBC_DATABASE_PASSWORD(ConfigArgs.JDBC_DATABASE_PASSWORD, DB_PW),

  // Default
  UNKNOWN(ConfigArgs.UNKNOWN);

  private String key;
  private ConfigArg alias;

  private static Map<String, ConfigArg> map = new ConcurrentHashMap<>();
  static {
    for (ConfigArg value : ConfigArg.values()) {
      map.put(value.getKey(), value);
    }
  }

  ConfigArg(String key) {
    this.key = key;
    this.alias = null;
  }

  ConfigArg(String key, ConfigArg alias) {
    this.key = key;
    this.alias = alias;
  }

  public static ConfigArg get(String arg) {
    ConfigArg configArg = map.getOrDefault(arg, UNKNOWN);

    if (configArg.hasAlias()) {
      return configArg.getAlias();
    }

    return map.getOrDefault(arg, UNKNOWN);
  }

  public boolean hasAlias() {
    return this.alias != null;
  }

  public String getKey() {
    return key;
  }

  public ConfigArg getAlias() {
    return alias;
  }
}