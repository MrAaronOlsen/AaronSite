package com.aaronsite.actions;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DBDelete;
import com.aaronsite.database.operations.DBUpdate;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Page;
import com.aaronsite.utils.enums.PageMode;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.ActionException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.bson.Document;

import static com.aaronsite.utils.enums.PageMode.CHECKED_OUT;
import static com.aaronsite.utils.enums.PageMode.PUBLISHED;
import static com.aaronsite.utils.exceptions.ActionException.Code.CANNOT_CHECK_IN_PAGE_NOT_CHECKED_OUT;
import static com.aaronsite.utils.exceptions.ActionException.Code.CHECK_IN_DID_NOT_UPDATE;
import static com.aaronsite.utils.exceptions.ActionException.Code.FOUND_MULTIPLE_PUBLISHED_PAGES;
import static com.aaronsite.utils.exceptions.ActionException.Code.FOUND_NO_PUBLISHED_PAGES;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckInPage implements Action {
  private String id;

  @Override
  public CheckInPage execute(Document doc) throws ABException {
    String toCheckIn = doc.getString("id");

    try (DBConnection conn = new DBConnection()) {
      // Retrieve live record to be checked in.
      DBResult queryResult = new DbQuery(conn, Table.PAGES)
          .setIdQuery(toCheckIn)
          .execute();

      if (queryResult.hasNext()) {
        Page pageToCheckIn = new Page(queryResult.getNext());

        // If the record is not checked out this action is invalid.
        if (PageMode.get(pageToCheckIn.getMode()) != CHECKED_OUT) {
          throw new ActionException(CANNOT_CHECK_IN_PAGE_NOT_CHECKED_OUT);
        }

        // Now try to find record to check in with.
        DBWhereStmtBuilder where = new DBWhereStmtBuilder()
            .add(Page.SLUG, pageToCheckIn.getSlug())
            .add(Page.MODE, PUBLISHED.getValue());

        // Validate that we only have one published page to check in with.
        DBResult publishedQuery = new DbQuery(conn, Table.PAGES)
            .setQuery(where)
            .doCount()
            .execute();

        if (publishedQuery.getCount() == 0) {
          throw new ActionException(FOUND_NO_PUBLISHED_PAGES);
        } else if (publishedQuery.getCount() > 1) {
          throw new ActionException(FOUND_MULTIPLE_PUBLISHED_PAGES);
        }

        // Now update existing record with changes from checked out version.
        pageToCheckIn.setMode(PUBLISHED);
        DBResult updateResult = new DBUpdate(conn, Table.PAGES)
            .setQuery(where)
            .setRecord(pageToCheckIn)
            .execute();

        if (updateResult.hasNext()) {
          id = updateResult.getNext().getId();

          // Delete the old checked out record.
          new DBDelete(conn, Table.PAGES)
              .setId(pageToCheckIn.getId())
              .execute();
        } else {
          throw new ActionException(CHECK_IN_DID_NOT_UPDATE);
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