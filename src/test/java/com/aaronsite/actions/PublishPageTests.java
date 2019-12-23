package com.aaronsite.actions;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.statements.DBSortStmtBuilder;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Page;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.aaronsite.utils.enums.PageMode.PUBLISHED;

public class PublishPageTests extends TestServer {

  @Test
  void canPublishARecordNoOtherRecordsPublished() throws ABException {
    String id = insertRecord(new Page()
        .setSlug("test"))
        .getId();

    String publishedId = new PublishPage()
        .execute(new Document("id", id).append("slug", "test"))
        .getId();

    try (DBConnection conn = new DBConnection()) {
      DBResult result = new DbQuery(conn, Table.PAGES)
          .setQuery(new DBWhereStmtBuilder(Page.MODE, PUBLISHED.getValue()))
          .doCount()
          .execute();

      Assertions.assertEquals(1, result.getCount());

      if (result.hasNext()) {
        Page publishedPage = result.getNext(Page.class);
        Assertions.assertEquals(publishedId, publishedPage.getId());
      } else {
        Assertions.fail("Should have found a published page.");
      }
    }
  }

  @Test
  void canPublishAlreadyPublishedRecord() throws ABException {
    String id = insertRecord(new Page()
        .setSlug("test")
        .setMode(PUBLISHED))
        .getId();

    String publishedId = new PublishPage()
        .execute(new Document("id", id).append("slug", "test"))
        .getId();

    try (DBConnection conn = new DBConnection()) {
      DBResult result = new DbQuery(conn, Table.PAGES)
          .setQuery(new DBWhereStmtBuilder(Page.MODE, PUBLISHED.getValue()))
          .doCount()
          .execute();

      Assertions.assertEquals(1, result.getCount());

      if (result.hasNext()) {
        Page publishedPage = result.getNext(Page.class);
        Assertions.assertEquals(publishedId, publishedPage.getId());
      } else {
        Assertions.fail("Should have found a published page.");
      }
    }
  }

  @Test
  void canPublishARecordMultipleRecordsAlreadyPublished() throws ABException {
    insertRecord(new Page()
        .setSlug("test")
        .setMode(PUBLISHED))
        .getId();

    insertRecord(new Page()
        .setSlug("test")
        .setMode(PUBLISHED))
        .getId();

    String id = insertRecord(new Page()
        .setSlug("test"))
        .getId();

    String publishedId = new PublishPage()
        .execute(new Document("id", id).append("slug", "test"))
        .getId();

    try (DBConnection conn = new DBConnection()) {
      DBResult result = new DbQuery(conn, Table.PAGES)
          .setQuery(new DBWhereStmtBuilder(Page.MODE, PUBLISHED.getValue()))
          .doCount()
          .execute();

      Assertions.assertEquals(1, result.getCount());

      if (result.hasNext()) {
        Page publishedPage = result.getNext(Page.class);
        Assertions.assertEquals(publishedId, publishedPage.getId());
      } else {
        Assertions.fail("Should have found a published page.");
      }
    }
  }

  @Test
  void publishingRecordDoesNotAffectOtherSlugs() throws ABException {
    insertRecord(new Page()
        .setSlug("test1")
        .setMode(PUBLISHED))
        .getId();

    insertRecord(new Page()
        .setSlug("test2")
        .setMode(PUBLISHED))
        .getId();

    String id = insertRecord(new Page()
        .setSlug("test3"))
        .getId();

    String publishedId = new PublishPage()
        .execute(new Document("id", id).append("slug", "test"))
        .getId();

    try (DBConnection conn = new DBConnection()) {
      DBResult result = new DbQuery(conn, Table.PAGES)
          .setQuery(new DBWhereStmtBuilder(Page.MODE, PUBLISHED.getValue()))
          .setSort(new DBSortStmtBuilder("slug"))
          .execute();

      List<DBRecord> records = result.getList();
      Assertions.assertEquals(3, records.size());

      Assertions.assertEquals("test1", new Page(records.get(0)).getSlug());
      Assertions.assertEquals("test2", new Page(records.get(1)).getSlug());

      Page newPublished = new Page(records.get(2));
      Assertions.assertEquals(publishedId, newPublished.getId());
      Assertions.assertEquals("test3", newPublished.getSlug());
    }
  }
}
