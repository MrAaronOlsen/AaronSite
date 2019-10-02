package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.utils.enums.Table;

public interface Model {
  DBRecord buildRecord();
  Table getTable();
  String toString();
}
