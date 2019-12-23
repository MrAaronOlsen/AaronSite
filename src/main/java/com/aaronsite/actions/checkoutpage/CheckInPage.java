package com.aaronsite.actions.checkoutpage;

import com.aaronsite.actions.Action;
import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DBInsert;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Page;
import com.aaronsite.utils.enums.PageMode;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.bson.Document;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckInPage implements Action {
  private String id;

  @Override
  public Action execute(Document doc) throws ABException {
    String id = doc.getString("id");

    try (DBConnection conn = new DBConnection()) {
      DBResult queryResult = new DbQuery(conn, Table.PAGES)
          .setIdQuery(id)
          .execute();

      if (queryResult.hasNext()) {
        Page pageToCopy = new Page(queryResult.getNext())
            .setMode(PageMode.CHECKED_OUT);

        DBResult insertResult = new DBInsert(conn, Table.PAGES)
            .addRecord(pageToCopy.buildRecord())
            .execute();

        if (insertResult.hasNext()) {
          id = insertResult.getNext().getId();
        }
      }
    }
    return this;
  }

  @JsonDeserialize
  public String getId() {
    return id;
  }
}