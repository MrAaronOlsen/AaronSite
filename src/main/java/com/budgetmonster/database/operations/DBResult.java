package com.budgetmonster.database.operations;

import com.budgetmonster.database.metadata.ResultMetadata;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBResult {
  private ResultSet result;
  private ResultMetadata resultMetadata;

  DBResult(ResultSet result) throws SQLException {
    this.result = result;
    this.resultMetadata = new ResultMetadata(result);
  }

  public boolean hasNext() throws SQLException {
    return result.next();
  }

  public DBRecord getNext() throws SQLException {
    if (resultMetadata == null) {
      resultMetadata = new ResultMetadata(result);
    }

    return new DBRecord(resultMetadata, result);
  }
}
