package com.aaronsite.utils.enums;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Role {
  READ("read"),
  UPDATE("update"),
  INSERT("insert"),
  DELETE("delete"),
  INVALID("");

  private String value;
  private static Map<String, Role> map = new ConcurrentHashMap<>();
  static {
    for (Role value : Role.values()) {
      map.put(value.getValue(), value);
    }
  }

  Role(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Role get(String role) {
    return map.getOrDefault(role, INVALID);
  }
}
