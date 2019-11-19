package com.aaronsite.database.metadata;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_GET_TABLE_METADATA;

public class TableMetadata {
  private static final Map<Table, TableMetadata> tableCache = new ConcurrentHashMap<>();

  private DBConnection dbConn;
  private Table table;
  private Map<String, ColumnMetadata> columns = new HashMap<>();

  public static TableMetadata getTableMetadata(DBConnection dbConn, Table table) throws DatabaseException {
    if (tableCache.containsKey(table)) {
      return tableCache.get(table);
    }

    TableMetadata tableMetadata = new TableMetadata(dbConn, table);
    tableCache.put(table, tableMetadata);

    return tableMetadata;
  }

  private TableMetadata(DBConnection dbConn, Table table) throws DatabaseException {
    this.dbConn = dbConn;
    this.table = table;

    build();
  }

  public Map<String, ColumnMetadata> getColumns() {
    return columns;
  }

  public ColumnMetadata getColumn(String column) {
    return columns.getOrDefault(column, ColumnMetadata.unknownColumn(column));
  }

  private void build() throws DatabaseException {
    try {
      ResultSet metaData = dbConn.getDbMetadata().
          getColumns(null, null, table.getName(), null);

      while (metaData.next()) {
        String name = metaData.getString("COLUMN_NAME");
        String type = metaData.getString("DATA_TYPE");

        ColumnMetadata columnMetaData = new ColumnMetadata.Builder()
            .setName(name)
            .setType(type)
            .build();

        columns.put(name, columnMetaData);
      }
    } catch (SQLException e) {
      throw new DatabaseException(FAILED_TO_GET_TABLE_METADATA).sqlEx(e);
    }
  }
}
