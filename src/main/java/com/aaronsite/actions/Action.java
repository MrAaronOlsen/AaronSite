package com.aaronsite.actions;

import com.aaronsite.response.ResponseData;
import com.aaronsite.utils.enums.ActionType;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.ActionException;
import org.bson.Document;

import static com.aaronsite.utils.exceptions.ActionException.Code.UNKNOWN_ACTION;

public interface Action extends ResponseData {

  static Action get(ActionType type) throws ActionException {
    switch (type) {
      case PUBLISH:
        return new PublishPage();
      case UNPUBLISH:
        return new UnpublishPage();
      case CHECK_OUT:
        return new CheckOutPage();
      case CHECK_IN:
        return new CheckInPage();
      default:
        throw new ActionException(UNKNOWN_ACTION, type.getValue());
    }
  }

  Action execute(Document doc) throws ABException;
}
