package com.aaronsite.utils.exceptions;

public class ActionException extends ABException {
  public enum Code implements ExceptionCode {
    PAGE_ALREADY_CHECKED_OUT("Cannot check out an already checked out page."),
    CANNOT_CHECK_IN_PAGE_NOT_CHECKED_OUT("Cannot check in a page not already checked out."),
    FOUND_MULTIPLE_PUBLISHED_PAGES("Cannot check in page %s because multiple published pages were found."),
    FOUND_NO_PUBLISHED_PAGES("Cannot check in page %s because no matching published version was found."),
    CHECK_IN_DID_NOT_UPDATE("Check In did not update any records."),
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
