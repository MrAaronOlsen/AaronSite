package com.aaronsite.actions;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DBUpdate;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
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
import static com.aaronsite.utils.enums.PageMode.PUBLISHED;
import static com.aaronsite.utils.exceptions.ActionException.Code.CANNOT_CHECK_IN_PAGE_NOT_CHECKED_OUT;
import static com.aaronsite.utils.exceptions.ActionException.Code.FOUND_NO_PUBLISHED_PAGES;

public class CheckInTests extends TestServer {

  @Test
  void canNotCheckInPageNotCheckedOut() throws ABException {
    Page page = new Page(insertRecord(new Page()
        .setSlug("test_slug")
        .setMode(PageMode.PUBLISHED)));

    CheckInPage checkIn = new CheckInPage();

    try {
      checkIn.execute(new Document("id", page.getId()));
      Assertions.fail("Should not have been able to Check Page In.");
    } catch (ActionException e) {
      Assertions.assertEquals(e.getCode(), CANNOT_CHECK_IN_PAGE_NOT_CHECKED_OUT);
    }
  }

  @Test
  void canCheckInCheckedOutPage() throws ABException {
    Page page = new Page(insertRecord(new Page()
        .setSlug("test_slug")
        .setHeader("test_header")
        .setMode(PageMode.PUBLISHED)));

    CheckOutPage checkOut = new CheckOutPage().execute(new Document("id", page.getId()));

    try (DBConnection conn = new DBConnection()) {
      // First assert we have a checked out page.
      DBResult beforeCheckIn = new DbQuery(conn, Table.PAGES)
          .setQuery(new DBWhereStmtBuilder(Page.MODE, CHECKED_OUT.getValue()))
          .execute();

      if (beforeCheckIn.hasNext()) {
        Page checkedOutPage = beforeCheckIn.getNext(Page.class);

        Assertions.assertEquals(checkOut.getId(), checkedOutPage.getId());

        // Update checked out page
        new DBUpdate(conn, Table.PAGES)
            .setQuery(checkedOutPage.getId())
            .setRecord(checkedOutPage.setHeader("new_header"))
            .execute();

      } else {
        Assertions.fail("Should have found a checked out record.");
      }

      // Now check in our checked out page.
      CheckInPage checkIn = new CheckInPage().execute(new Document("id", checkOut.getId()));

      // Make sure our checked out page no longer exists.
      DBResult checkedOut = new DbQuery(conn, Table.PAGES)
          .setIdQuery(checkOut.getId())
          .execute();

      if (checkedOut.hasNext()) {
        Assertions.fail("Should not have found any checked out records.");
      }

      // Now make sure our published page has the modifications
      DBResult publishedPage = new DbQuery(conn, Table.PAGES)
          .setIdQuery(checkIn.getId())
          .execute();

      if (publishedPage.hasNext()) {
        Page updatedPage = publishedPage.getNext(Page.class);

        Assertions.assertEquals(page.getId(), updatedPage.getId());
        Assertions.assertEquals("new_header", updatedPage.getHeader());
        Assertions.assertEquals(PUBLISHED, PageMode.get(updatedPage.getMode()));
      } else {
        Assertions.fail("Should have found original published page.");
      }
    }
  }

  @Test
  void cannotCheckInIfSlugIsModified() throws ABException {
    Page page = new Page(insertRecord(new Page()
        .setSlug("test_slug")
        .setMode(PageMode.PUBLISHED)));

    CheckOutPage checkOut = new CheckOutPage().execute(new Document("id", page.getId()));

    try (DBConnection conn = new DBConnection()) {
      // First assert we have a checked out page.
      DBResult beforeCheckIn = new DbQuery(conn, Table.PAGES)
          .setQuery(new DBWhereStmtBuilder(Page.MODE, CHECKED_OUT.getValue()))
          .execute();

      if (beforeCheckIn.hasNext()) {
        Page checkedOutPage = beforeCheckIn.getNext(Page.class);

        Assertions.assertEquals(checkOut.getId(), checkedOutPage.getId());

        // Update checked out page
        new DBUpdate(conn, Table.PAGES)
            .setQuery(checkedOutPage.getId())
            .setRecord(checkedOutPage.setSlug("new_slug"))
            .execute();

      } else {
        Assertions.fail("Should have found a checked out record.");
      }

      try {
        new CheckInPage().execute(new Document("id", checkOut.getId()));
        Assertions.fail("Should have failed to check in page.");
      } catch (ActionException e) {
        Assertions.assertEquals(e.getCode(), FOUND_NO_PUBLISHED_PAGES);
      }
    }
  }
}
