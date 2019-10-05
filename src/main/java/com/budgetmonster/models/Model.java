package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.response.Data;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.ModelException;

import java.util.function.Function;

import static com.budgetmonster.utils.exceptions.ModelException.Code.INVALID_MODEL_TABLE;

public interface Model {
  static Function<DBRecord, Data> getModelData(Table table) throws ABException {
    switch (table) {
      case BUDGET: return Budget::new;
      default:
        throw new ModelException(INVALID_MODEL_TABLE, table.getName());
    }
  }

  static Function<DBRecord, Model> getModel(Table table) throws ABException {
    switch (table) {
      case BUDGET: return Budget::new;
      default:
        throw new ModelException(INVALID_MODEL_TABLE, table.getName());
    }
  }

  DBRecord buildRecord();
  Table getTable();
  String toString();
}
