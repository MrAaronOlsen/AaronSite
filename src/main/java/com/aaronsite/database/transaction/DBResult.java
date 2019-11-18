package com.aaronsite.database.transaction;

import com.aaronsite.database.metadata.ResultMetadata;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_GET_NEXT_RESULT;

public class DBResult {
  private ResultSet result;
  private ResultMetadata resultMetadata;

  public DBResult(ResultSet result) throws DatabaseException {
    if (result != null) {
      this.result = result;
      this.resultMetadata = new ResultMetadata(result);
    }
  }

  public boolean hasNext() throws DatabaseException {
    try {
      return result.next();
    } catch (SQLException e) {
      throw new DatabaseException(FAILED_TO_GET_NEXT_RESULT).sqlEx(e);
    }
  }

  public DBRecord getNext() throws DatabaseException {
    if (resultMetadata == null) {
      resultMetadata = new ResultMetadata(result);
    }

    return new DBRecord(resultMetadata, result);
  }
}
