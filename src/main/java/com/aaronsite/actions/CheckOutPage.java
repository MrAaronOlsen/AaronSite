package com.aaronsite.actions;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DBInsert;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Page;
import com.aaronsite.utils.enums.PageMode;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.ActionException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.bson.Document;

import static com.aaronsite.utils.exceptions.ActionException.Code.PAGE_ALREADY_CHECKED_OUT;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckOutPage implements Action {
  private String id;

  @Override
  public CheckOutPage execute(Document doc) throws ABException {
    String toCheckOut = doc.getString("id");

    try (DBConnection conn = new DBConnection()) {
      DBResult queryResult = new DbQuery(conn, Table.PAGES)
          .setIdQuery(toCheckOut)
          .execute();

      if (queryResult.hasNext()) {
        Page pageToCopy = new Page(queryResult.getNext());
        if (PageMode.get(pageToCopy.getMode()) == PageMode.CHECKED_OUT) {
          throw new ActionException(PAGE_ALREADY_CHECKED_OUT);
        }

        pageToCopy.setMode(PageMode.CHECKED_OUT);

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