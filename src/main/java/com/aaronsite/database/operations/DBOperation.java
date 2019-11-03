package com.aaronsite.database.operations;

import com.aaronsite.utils.exceptions.DatabaseException;

public interface DBOperation {
  DBResult execute() throws DatabaseException;
}
