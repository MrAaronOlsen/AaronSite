package com.aaronsite.actions;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Page;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.aaronsite.utils.enums.PageMode.NONE;
import static com.aaronsite.utils.enums.PageMode.PUBLISHED;

public class UnpublishPageTests extends TestServer {

  @Test
  void canUnpublishAPublishedRecord() throws ABException {
    String id = insertRecord(new Page()
        .setSlug("test")
        .setMode(PUBLISHED))
        .getId();

    String unPublished = new UnpublishPage()
        .execute(new Document("id", id).append("slug", "test"))
        .getId();

    try (DBConnection conn = new DBConnection()) {
      DBResult result = new DbQuery(conn, Table.PAGES)
          .setQuery(new DBWhereStmtBuilder(Page.MODE, NONE.getValue()))
          .doCount()
          .execute();

      Assertions.assertEquals(1, result.getCount());

      if (result.hasNext()) {
        Page publishedPage = result.getNext(Page.class);
        Assertions.assertEquals(unPublished, publishedPage.getId());
      } else {
        Assertions.fail("Should have found an unpublished page.");
      }
    }
  }
}
