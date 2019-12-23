package com.aaronsite.utils.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ActionType {
  CHECK_OUT("check_out"),
  INVALID("invalid");

  private String value;

  ActionType(String value) {
    this.value = value;
  }

  private static Map<String, ActionType> MAP =
      Arrays.stream(ActionType.values()).collect(Collectors.toConcurrentMap(ActionType::getValue, role -> role));

  public String getValue() {
    return value;
  }

  public static ActionType get(String action) {
    return MAP.getOrDefault(action, INVALID);
  }
}
