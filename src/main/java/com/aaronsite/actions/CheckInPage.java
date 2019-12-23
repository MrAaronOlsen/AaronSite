package com.aaronsite.actions;

import com.aaronsite.actions.Action;
import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DBDelete;
import com.aaronsite.database.operations.DBUpdate;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Page;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.bson.Document;

import static com.aaronsite.utils.enums.PageMode.PUBLISHED;

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
        Page pageToCheckIn = new Page(queryResult.getNext())
            .setMode(PUBLISHED);

        DBWhereStmtBuilder where = new DBWhereStmtBuilder()
            .add(Page.HEADER, pageToCheckIn.getHeader())
            .add(Page.MODE, PUBLISHED.getValue());

        DBResult updateResult = new DBUpdate(conn, Table.PAGES)
            .setQuery(where)
            .setRecord(pageToCheckIn)
            .execute();

        if (updateResult.hasNext()) {
          id = updateResult.getNext().getId();

          new DBDelete(conn, Table.PAGES)
              .setId(pageToCheckIn.getId())
              .execute();
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