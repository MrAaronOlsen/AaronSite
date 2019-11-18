package com.aaronsite.database.metadata;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.models.TestSimple;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TableMetadataTests extends TestServer {

  @Test
  void canCreateTableMetadata() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      TableMetadata metadata = TableMetadata.getTableMetadata(conn, Table.TEST_SIMPLE);

      ColumnMetadata id = metadata.getColumn(TestSimple.ID);
      Assertions.assertEquals(id.getType(), ColumnMetadata.Type.INTEGER);

      ColumnMetadata name = metadata.getColumn(TestSimple.NAME);
      Assertions.assertEquals(name.getType(), ColumnMetadata.Type.STRING);

      ColumnMetadata text = metadata.getColumn(TestSimple.TEXT);
      Assertions.assertEquals(text.getType(), ColumnMetadata.Type.STRING);
    }
  }

}
