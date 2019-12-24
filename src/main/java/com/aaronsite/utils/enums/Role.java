package com.aaronsite.utils.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Role {
  READ("read"),
  UPDATE("update"),
  INSERT("insert"),
  DELETE("delete"),
  INVALID("");

  private String value;
  private static Map<String, Role> map =
      Arrays.stream(Role.values()).collect(Collectors.toConcurrentMap(Role::getValue, role -> role));

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
