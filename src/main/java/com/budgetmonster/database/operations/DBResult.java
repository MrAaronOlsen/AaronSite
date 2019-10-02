package com.budgetmonster.database.operations;

import com.budgetmonster.database.metadata.ResultMetadata;
import com.budgetmonster.utils.exceptions.DBException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.budgetmonster.utils.exceptions.DBException.Code.FAILED_TO_GET_NEXT_RESULT;

public class DBResult {
  private ResultSet result;
  private ResultMetadata resultMetadata;

  DBResult(ResultSet result) throws DBException {
    this.result = result;
    this.resultMetadata = new ResultMetadata(result);
  }

  public boolean hasNext() throws DBException {
    try {
      return result.next();
    } catch (SQLException e) {
      throw new DBException(FAILED_TO_GET_NEXT_RESULT).sqlEx(e);
    }
  }

  public DBRecord getNext() throws DBException {
    if (resultMetadata == null) {
      resultMetadata = new ResultMetadata(result);
    }

    return new DBRecord(resultMetadata, result);
  }
}
