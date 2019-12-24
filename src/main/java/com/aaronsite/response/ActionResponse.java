package com.aaronsite.response;

import com.aaronsite.actions.Action;
import com.aaronsite.utils.enums.ActionType;
import com.aaronsite.utils.exceptions.ABException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ActionResponse {

  static Response build(String body) throws ABException {
    return new Response(executeAction(Document.parse(body)));
  }

  private static List<ResponseData> executeAction(Document doc) throws ABException {
    List<ResponseData> results = new ArrayList<>();

    ActionType action = ActionType.get(doc.getString("action"));

    results.add(Action.get(action).execute(doc));

    return results;
  }
}
