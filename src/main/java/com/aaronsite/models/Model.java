package com.aaronsite.models;

import com.aaronsite.database.operations.DBRecord;
import com.aaronsite.response.ResponseData;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.ModelException;

import java.util.function.Function;

import static com.aaronsite.utils.exceptions.ModelException.Code.INVALID_MODEL_TABLE;

public interface Model extends ResponseData {

  static Function<DBRecord, Model> getModel(Table table) throws ABException {
    switch (table) {
      case POSTS: return Post::new;
      default:
        throw new ModelException(INVALID_MODEL_TABLE, table.getName());
    }
  }

  String getId();
  DBRecord buildRecord();
  Table getTable();
  String toString();
}
