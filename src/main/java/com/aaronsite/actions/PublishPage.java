package com.aaronsite.actions;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Page;
import com.aaronsite.utils.enums.PageMode;
import com.aaronsite.utils.exceptions.ABException;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import static com.aaronsite.utils.enums.Table.PAGES;

public class PublishPage implements Action {
  private String id;

  @Override
  public Action execute(Document doc) throws ABException {
    String toPublish = doc.getString("id");

    try (DBConnection conn = new DBConnection()) {
      DBResult result = new DbQuery(conn, PAGES)
          .setQuery(new DBWhereStmtBuilder().add(Page.MODE, PageMode.PUBLISHED.getValue()))
          .doCount()
          .execute();

      while (result.hasNext()) {
        Page published = result.getNext(Page.class);

        if (StringUtils.equals(toPublish, published.getId())) {
          id = toPublish;
          return this;
        }


      }
    }

    return this;
  }

  public String getId() {
    return id;
  }
}
