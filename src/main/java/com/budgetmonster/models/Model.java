package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.response.ResponseData;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.ModelException;

import java.util.function.Function;

import static com.budgetmonster.utils.exceptions.ModelException.Code.INVALID_MODEL_TABLE;

public interface Model extends ResponseData {

  static Function<DBRecord, Model> getModel(Table table) throws ABException {
    switch (table) {
      case BUDGET: return Budget::new;
      case TRANSACTION: return Transaction::new;
      case MONTH: return Month::new;
      case PERIOD: return Period::new;
      case BUDGET_PERIODS: return BudgetPeriod::new;
      case BUDGET_PERIOD_TRANSACTION: return BudgetPeriodTransaction::new;
      default:
        throw new ModelException(INVALID_MODEL_TABLE, table.getName());
    }
  }

  DBRecord buildRecord();
  Table getTable();
  String toString();
}
