package com.budgetmonster.database.operations;

import java.sql.SQLException;

public interface DBOperation {
  DBResult execute() throws SQLException;
}
