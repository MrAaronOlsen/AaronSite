package com.budgetmonster.utils.exceptions;

public class ModelException extends ABException {
  public enum Code {
    INVALID_MODEL_TABLE("No model exists for table %s.");

    private String message;

    Code(String message) {
      this.message = message;
    }

    public String format(String... args) {
      return String.format(message, args);
    }
  }

  private ModelException.Code code;
  private String[] args;

  public ModelException(ModelException.Code code, String... args) {
    this.code = code;
    this.args = args;
  }

  @Override
  public String getMessage() {
    return code.format(args);
  }
}
