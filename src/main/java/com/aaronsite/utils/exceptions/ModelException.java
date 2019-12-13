package com.aaronsite.utils.exceptions;

public class ModelException extends ABException {
  public enum Code implements ExceptionCode {
    INVALID_MODEL_TABLE("No model exists for table %s."),
    MODEL_PROCESSING_ERROR("Failed to process model. ERROR: %s");

    private String message;

    Code(String message) {
      this.message = message;
    }

    @Override
    public String getMessage() {
      return message;
    }

    @Override
    public String getName() {
      return null;
    }
  }

  public ModelException(ModelException.Code code, String... args) {
    super(code, args);
  }
}
