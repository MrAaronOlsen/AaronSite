package com.aaronsite.database.metadata;

public class ColumnMetadata {
  private String name;
  private String type;

  private ColumnMetadata(Builder builder) {
    this.name = builder.name;
    this.type = builder.type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public static class Builder {
    private String name;
    private String type;

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setType(String type) {
      this.type = type;
      return this;
    }

    public ColumnMetadata build() {
      return new ColumnMetadata(this);
    }
  }
}
