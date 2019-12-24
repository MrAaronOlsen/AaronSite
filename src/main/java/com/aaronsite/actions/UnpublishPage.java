package com.aaronsite.actions;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DBUpdate;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Page;
import com.aaronsite.utils.enums.PageMode;
import com.aaronsite.utils.exceptions.ABException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.bson.Document;

import static com.aaronsite.utils.enums.Table.PAGES;

public class UnpublishPage implements Action {
  private String id;

  @Override
  public UnpublishPage execute(Document doc) throws ABException {
    id = doc.getString("id");

    try (DBConnection conn = new DBConnection()) {
      DBResult result = new DbQuery(conn, PAGES)
          .setIdQuery(id)
          .execute();

      if (result.hasNext()) {
        Page published = result.getNext(Page.class);

        // If the record isn't published don't do anything
        if (PageMode.get(published.getMode()) == PageMode.PUBLISHED) {

          // Set the mode to none
          DBResult updateResult = new DBUpdate(conn, PAGES)
              .setQuery(published.getId())
              .setRecord(published.setMode(PageMode.NONE))
              .execute();

          if (updateResult.hasNext()) {
            Page unPublished = updateResult.getNext(Page.class);
            id = unPublished.getId();
          }
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
