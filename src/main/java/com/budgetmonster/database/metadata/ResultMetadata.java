package com.budgetmonster.database.metadata;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultMetadata {
  private ResultSet resultSet;
  private ColumnMetadata[] columns;

  public ResultMetadata(ResultSet resultSet) throws SQLException {
    this.resultSet = resultSet;

    build();
  }

  public ColumnMetadata[] getColumns() {
    return columns;
  }

  private void build() throws SQLException {
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
  }
}
