package com.aaronsite.database.metadata;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_GET_TABLE_METADATA;

public class TableMetadata {
  private static final Map<String, TableMetadata> tableCache = new ConcurrentHashMap<>();

  private DBConnection dbConn;
  private String table;
  private Map<String, ColumnMetadata> columns = new HashMap<>();

  public static TableMetadata getTableMetadata(DBConnection dbConn, String table) throws DatabaseException {
    if (tableCache.containsKey(table)) {
      return tableCache.get(table);
    }

    TableMetadata tableMetadata = new TableMetadata(dbConn, table);
    tableCache.put(table, tableMetadata);

    return tableMetadata;
  }

  private TableMetadata(DBConnection dbConn, String table) throws DatabaseException {
    this.dbConn = dbConn;
    this.table = table;

    build();
  }

  public Map<String, ColumnMetadata> getColumns() {
    return columns;
  }

  private void build() throws DatabaseException {
    try {
      ResultSetMetaData metaData = dbConn.getDbMetadata().getTables(null, null, table, null).getMetaData();

      for (int i = 1; i <= metaData.getColumnCount(); i++) {
        ColumnMetadata columnMetaData = new ColumnMetadata.Builder()
            .setName(metaData.getColumnName(i))
            .setType(metaData.getColumnTypeName(i))
            .build();

        columns.put(columnMetaData.getName(), columnMetaData);
      }
    } catch (SQLException e) {
      throw new DatabaseException(FAILED_TO_GET_TABLE_METADATA).sqlEx(e);
    }
  }
}
