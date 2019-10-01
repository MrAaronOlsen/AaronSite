package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;

public interface Model {
  DBRecord buildRecord();
}
