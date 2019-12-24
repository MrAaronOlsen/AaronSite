package com.aaronsite.database.transaction;

import com.aaronsite.database.metadata.ResultMetadata;
import com.aaronsite.models.Model;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.aaronsite.utils.exceptions.DatabaseException.Code.DO_COUNT_NOT_SET;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_GET_NEXT_RESULT;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.INVALID_MODEL_INVOCATION;

public class DBResult {
  private ResultSet result;
  private ResultMetadata resultMetadata;
  private Integer count;

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

  public <T extends Model> T getNext(Class<T> model) throws DatabaseException {
    if (resultMetadata == null) {
      resultMetadata = new ResultMetadata(result);
    }

    try {
      return model.getConstructor(DBRecord.class)
          .newInstance(new DBRecord(resultMetadata, result));
    } catch (Exception e) {
      throw new DatabaseException(INVALID_MODEL_INVOCATION, model.toGenericString());
    }
  }

  public List<DBRecord> getList() throws DatabaseException {
    if (resultMetadata == null) {
      resultMetadata = new ResultMetadata(result);
    }

    List<DBRecord> results = new ArrayList<>();

    while (hasNext()) {
      results.add(getNext());
    }

    return results;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public Integer getCount() throws DatabaseException {
    if (count == null) {
      throw new DatabaseException(DO_COUNT_NOT_SET);
    }

    return count;
  }
}
