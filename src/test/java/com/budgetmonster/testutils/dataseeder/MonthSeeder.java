package com.budgetmonster.testutils.dataseeder;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.models.Month;
import com.budgetmonster.utils.exceptions.ABException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthSeeder extends DataSeeder {
  private final Map<String, Month> MONTHS = new HashMap<>();

  @Override
  public void seed() throws ABException {
    List<Document> months = loadResourceAsDocument("months.json").getList("months", Document.class);

    for (Document monthIn : months) {
      Month month = new Month(insertRecord(new Month(new DBRecord(monthIn))));
      MONTHS.put(month.getShortName(), month);
    }
  }

  @Override
  public Month get(String nameShort) {
    return MONTHS.get(nameShort);
  }

  @Override
  public Map<String, Month> getMap() {
    return MONTHS;
  }

  @Override
  public List<Month> getList() {
    return (ArrayList<Month>) MONTHS.values();
  }
}
