package com.aaronsite.utils.exceptions;

public class TriggerException extends ABException {
  public enum Code implements ExceptionCode {
    CANNOT_UPDATE_PUBLISHED_PAGE("Cannot update a published page. Check page out first."),
    CANNOT_DELETE_PUBLISHED_PAGE("Cannot delete a published page. Unpublish page first.");

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

  public TriggerException(TriggerException.Code code, String... args) {
    super(code, args);
  }
}
