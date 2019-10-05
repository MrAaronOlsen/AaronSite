package com.budgetmonster.database.operations;

import com.budgetmonster.utils.exceptions.DatabaseException;

public interface DBOperation {
  DBResult execute() throws DatabaseException;
}
