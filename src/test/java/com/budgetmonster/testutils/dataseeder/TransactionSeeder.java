package com.budgetmonster.testutils.dataseeder;

import com.budgetmonster.models.Model;
import com.budgetmonster.models.Transaction;
import com.budgetmonster.utils.exceptions.ABException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionSeeder extends DataSeeder {
  private final List<Transaction> STAGED_TRANSACTIONS = new ArrayList<>();
  private final Map<String, Transaction> TRANSACTIONS = new HashMap<>();

  @Override
  public void seed() throws ABException {
    for (Transaction staged : STAGED_TRANSACTIONS) {
      TRANSACTIONS.put(staged.getVendor(), new Transaction(insertRecord(staged)));
    }
  }

  @Override
  public Transaction get(String key) {
    return TRANSACTIONS.get(key);
  }

  @Override
  public Map<String, ? extends Model> getMap() {
    return null;
  }

  @Override
  public List<? extends Model> getList() {
    return null;
  }

  public TransactionSeeder stage(String vendor, String amount, String date) {
    Transaction transactionIn = new Transaction()
        .setVendor(vendor)
        .setDate(date)
        .setAmount(amount);

    STAGED_TRANSACTIONS.add(transactionIn);
    return this;
  }
}
