package com.aaronsite.database.metadata;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ColumnMetadata {
  public enum Type {
    STRING("12"),
    INTEGER("4"),
    UNKNOWN("");

    private static final Map<String, Type> codeMap = Arrays.stream(Type.values()).collect(
        Collectors.toUnmodifiableMap(Type::getCode, type -> type));

    String code;

    Type(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }

    public static Type getType(String code) {
      return codeMap.getOrDefault(code, UNKNOWN);
    }
  }
  private String name;
  private Type type;

  private ColumnMetadata(Builder builder) {
    this.name = builder.name;
    this.type = builder.type;
  }

  public String getName() {
    return name;
  }

  public Type getType() {
    return type;
  }

  public static ColumnMetadata unknownColumn(String name) {
    return new ColumnMetadata.Builder()
        .setName(name).build();
  }

  public static class Builder {
    private String name;
    private Type type = Type.UNKNOWN;

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setType(String type) {
      this.type = Type.getType(type);
      return this;
    }

    public ColumnMetadata build() {
      return new ColumnMetadata(this);
    }
  }
}
