package com.aaronsite.database.statements;

import com.aaronsite.database.metadata.ColumnMetadata;
import com.aaronsite.utils.exceptions.DatabaseException;

import static com.aaronsite.utils.exceptions.DatabaseException.Code.UNKNOWN_COLUMN;

class DBStmtSetter {
  private DBPreparedStmt stmt;
  private int index = 1;

  DBStmtSetter(DBPreparedStmt stmt) {
    this.stmt = stmt;
  }

  void setValue(ColumnMetadata column, String value) throws DatabaseException {

    switch (column.getType()) {
      case INTEGER:
        stmt.set(index, Integer.valueOf(value));
        break;
      case STRING:
        stmt.set(index, value);
        break;
      default: throw new DatabaseException(UNKNOWN_COLUMN, column.getName());
    }

    index++;
  }
}
