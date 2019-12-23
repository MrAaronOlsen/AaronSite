package com.aaronsite.utils.exceptions;

public class ActionException extends ABException {
  public enum Code implements ExceptionCode {
    PAGE_ALREADY_CHECKED_OUT("Cannot check out an already checked out page."),
    DID_NOT_PUBLISH_PAGE("No records were updated to published.");

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

  public ActionException(ActionException.Code code, String... args) {
    super(code, args);
  }
}
