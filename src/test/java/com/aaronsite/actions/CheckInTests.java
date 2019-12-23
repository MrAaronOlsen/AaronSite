package com.aaronsite.actions;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Page;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.enums.PageMode;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.ActionException;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.aaronsite.utils.enums.PageMode.CHECKED_OUT;
import static com.aaronsite.utils.exceptions.ActionException.Code.PAGE_ALREADY_CHECKED_OUT;

public class CheckInTests extends TestServer {

  @Test
  void canCheckOutAPublishedPage() throws ABException {
    Page page = new Page(insertRecord(new Page()
        .setSlug("test_slug")
        .setHeader("test header")
        .setMode(PageMode.PUBLISHED)));

    CheckOutPage checkOut = new CheckOutPage()
        .execute(new Document("id", page.getId()));

    String id = checkOut.getId();

    try (DBConnection conn = new DBConnection()) {
      DBResult result = new DbQuery(conn, Table.PAGES)
          .setIdQuery(id)
          .execute();

      if (result.hasNext()) {
        Page checkedOutPage = new Page(result.getNext());

        Assertions.assertEquals(checkedOutPage.getMode(), CHECKED_OUT.getValue());
      } else {
        Assertions.fail("Should have returned a checked out record.");
      }
    }
  }

  @Test
  void canNotCheckOutACheckedOutPage() throws ABException {
    Page page = new Page(insertRecord(new Page()
        .setSlug("test_slug")
        .setHeader("test header")
        .setMode(CHECKED_OUT)));

    try {
      new CheckOutPage()
          .execute(new Document("id", page.getId()));
    } catch (ActionException e) {
      Assertions.assertEquals(e.getCode(), PAGE_ALREADY_CHECKED_OUT);
    }
  }
}
