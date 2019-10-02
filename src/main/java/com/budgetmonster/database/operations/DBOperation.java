package com.budgetmonster.database.operations;

import com.budgetmonster.utils.exceptions.DBException;

public interface DBOperation {
  DBResult execute() throws DBException;
}
