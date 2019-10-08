package com.budgetmonster.testutils.dataseeder;

import com.budgetmonster.models.Month;
import com.budgetmonster.models.Period;
import com.budgetmonster.utils.exceptions.ABException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodSeeder extends DataSeeder {
  private final List<Period> STAGED_PERIODS = new ArrayList<>();
  private final Map<String, Period> PERIODS = new HashMap<>();

  @Override
  public void seed() throws ABException {
    for (Period staged : STAGED_PERIODS) {
      PERIODS.put(staged.getYear() + staged.getMonthNumber(), new Period(insertRecord(staged)));
    }
  }

  @Override
  public Period get(String yearMonthNumber) {
    return PERIODS.get(yearMonthNumber);
  }

  @Override
  public Map<String, Period> getMap() {
    return PERIODS;
  }

  @Override
  public List<Period> getList() {
    return (ArrayList<Period>) PERIODS.values();
  }

  public PeriodSeeder stage(List<Month> months, String... years) {
    for (Month month : months) {
      for (String year : years) {
        Period period = new Period()
            .setMonthNumber(month.getNumber())
            .setYear(year);

        STAGED_PERIODS.add(period);
      }
    }

    return this;
  }
}
