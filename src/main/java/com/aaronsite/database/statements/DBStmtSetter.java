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
        stmt.setInteger(index, value);
        break;
      case STRING:
        stmt.setString(index, value);
        break;
      case JSON:
        stmt.setJson(index, value);
        break;
      default:
        throw new DatabaseException(UNKNOWN_COLUMN, column.getName());
    }

    index++;
  }
}
