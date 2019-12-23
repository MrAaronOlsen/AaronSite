package com.aaronsite.response;

import com.aaronsite.actions.CheckInPage;
import com.aaronsite.actions.CheckOutPage;
import com.aaronsite.actions.PublishPage;
import com.aaronsite.utils.enums.ActionType;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.ResponseException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.aaronsite.utils.exceptions.ResponseException.Code.UNKNOWN_ACTION;

public class ActionResponse {

  static Response build(String body) throws ABException {
    return new Response(executeAction(Document.parse(body)));
  }

  private static List<ResponseData> executeAction(Document doc) throws ABException {
    List<ResponseData> results = new ArrayList<>();

    ActionType action = ActionType.get(doc.getString("action"));

    switch (action) {
      case PUBLISH:
        results.add(new PublishPage().execute(doc));
        break;
      case CHECK_OUT:
        results.add(new CheckOutPage().execute(doc));
        break;
      case CHECK_IN:
        results.add(new CheckInPage().execute(doc));
        break;
      default:
        throw new ResponseException(UNKNOWN_ACTION, doc.getString("action"));

    }

    return results;
  }
}
