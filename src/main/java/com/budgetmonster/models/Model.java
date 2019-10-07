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
      case BUDGETS: return Budget::new;
      case TRANSACTIONS: return Transaction::new;
      case MONTHS: return Month::new;
      case PERIODS: return Period::new;
      case BUDGET_PERIODS: return BudgetPeriod::new;
      case BUDGET_PERIOD_TRANSACTIONS: return BudgetPeriodTransaction::new;
      default:
        throw new ModelException(INVALID_MODEL_TABLE, table.getName());
    }
  }

  DBRecord buildRecord();
  Table getTable();
  String toString();
}
