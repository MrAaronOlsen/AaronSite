package com.aaronsite.utils.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PageMode {
  PUBLISHED("published"),
  CHECKED_OUT("checked_out"),
  ARCHIVED("archived"),
  NONE("none"),
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
    if (mode == null) {
      return NONE;
    }

    return MAP.getOrDefault(mode, INVALID);
  }
}
