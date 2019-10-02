package com.budgetmonster.database.metadata;

import com.budgetmonster.utils.exceptions.DBException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static com.budgetmonster.utils.exceptions.DBException.Code.FAILED_TO_BUILD_RESULT_METADATA;

public class ResultMetadata {
  private ResultSet resultSet;
  private ColumnMetadata[] columns;

  public ResultMetadata(ResultSet resultSet) throws DBException {
    this.resultSet = resultSet;

    build();
  }

  public ColumnMetadata[] getColumns() {
    return columns;
  }

  private void build() throws DBException {
    try {
      ResultSetMetaData metaData = resultSet.getMetaData();
      int columnCount = metaData.getColumnCount();
      columns = new ColumnMetadata[columnCount];

      for (int i = 0; i < columnCount; i++) {
        ColumnMetadata column = new ColumnMetadata.Builder()
            .setName(metaData.getColumnName(i + 1))
            .setType(metaData.getColumnTypeName(i + 1))
            .build();

        columns[i] = column;
      }
    } catch (SQLException e) {
      throw new DBException(FAILED_TO_BUILD_RESULT_METADATA).sqlEx(e);
    }
  }
}
