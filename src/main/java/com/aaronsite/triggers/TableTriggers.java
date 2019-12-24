package com.aaronsite.triggers;

import com.aaronsite.models.Model;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.TriggerException;

public abstract class TableTriggers {

  public static TableTriggers get(Table table) {
    switch (table) {
      case PAGES:
        return new PageTriggers();
      default:
        return null;
    }
  }

  public void preUpdate(Model model) throws TriggerException {
    // No Op
  }

  public void preDelete(Model model) throws TriggerException {
    // No Op
  }
}
