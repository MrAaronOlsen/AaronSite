package com.aaronsite.utils.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PageMode {
  PUBLISHED("published"),
  CHECKED_OUT("checked_out"),
  INVALID("invalid");

  private String value;

  PageMode(String value) {
    this.value = value;
  }

  private static Map<String, PageMode> MAP =
      Arrays.stream(PageMode.values()).collect(Collectors.toConcurrentMap(PageMode::getValue, role -> role));

  public String getValue() {
    return value;
  }

  public static PageMode get(String mode) {
    return MAP.getOrDefault(mode, INVALID);
  }
}
