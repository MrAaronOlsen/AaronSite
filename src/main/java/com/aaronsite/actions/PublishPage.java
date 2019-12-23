package com.aaronsite.actions;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DBUpdate;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Page;
import com.aaronsite.utils.enums.PageMode;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.ActionException;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import static com.aaronsite.utils.enums.Table.PAGES;
import static com.aaronsite.utils.exceptions.ActionException.Code.DID_NOT_PUBLISH_PAGE;

public class PublishPage implements Action {
  private String id;

  @Override
  public PublishPage execute(Document doc) throws ABException {
    String toPublishId = doc.getString("id");
    String toPublishSlug = doc.getString("slug");

    try (DBConnection conn = new DBConnection()) {
      DBResult result = new DbQuery(conn, PAGES)
          .setQuery(new DBWhereStmtBuilder()
              .add(Page.MODE, PageMode.PUBLISHED.getValue())
              .add(Page.SLUG, toPublishSlug))
          .execute();

      while (result.hasNext()) {
        Page published = result.getNext(Page.class);

        // If it's already published set this record as the published id.
        if (StringUtils.equals(toPublishId, published.getId())) {
          id = toPublishId;
          continue;
        }

        // If it's not published update mode to Archived
        new DBUpdate(conn, PAGES)
            .setQuery(published.getId())
            .setRecord(published.setMode(PageMode.ARCHIVED))
            .execute();
      }

      // If we still don't have a published id update page as published.
      if (StringUtils.isEmpty(id)) {
        DBResult result1 = new DBUpdate(conn, PAGES)
            .setQuery(toPublishId)
            .setRecord(new DBRecord().add(Page.MODE, PageMode.PUBLISHED.getValue()))
            .execute();

        if (result1.hasNext()) {
          id = result1.getNext(Page.class).getId();
        } else {
          throw new ActionException(DID_NOT_PUBLISH_PAGE);
        }
      }
    }

    return this;
  }

  public String getId() {
    return id;
  }
}
