package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.response.Data;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.SimpleMessageException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.function.Function;

public interface Model {
  static Function<DBRecord, Data> getModelData(Table table) throws ABException {
    switch (table) {
      case BUDGET:
        return Budget::new;
      default:
        throw new SimpleMessageException("Bad Bad Bad");
    }
  }

  static Function<DBRecord, Model> getModel(Table table) throws ABException {
    switch (table) {
      case BUDGET:
        return Budget::new;
      default:
        throw new SimpleMessageException("Bad Bad Bad");
    }
  }

  DBRecord buildRecord();
  Table getTable();
  String toString();
}
