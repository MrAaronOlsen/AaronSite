package com.aaronsite.models;

import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.response.ResponseData;
import com.aaronsite.security.Authentication;
import com.aaronsite.utils.annotations.Column;
import com.aaronsite.utils.enums.ColumnType;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.ModelException;

import java.lang.reflect.Field;
import java.util.function.Function;

import static com.aaronsite.utils.exceptions.ModelException.Code.INVALID_MODEL_TABLE;

public interface Model extends ResponseData {

  static Function<DBRecord, Model> getModel(Table table) throws ABException {
    switch (table) {
      case PAGES: return Page::new;
      case USERS: return User::new;
      default:
        throw new ModelException(INVALID_MODEL_TABLE, table.getName());
    }
  }

  static void processTypes(Model model) throws IllegalAccessException {
    Class<?> clazz = model.getClass();

    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);

      if (field.isAnnotationPresent(Column.class)) {
        Column an = field.getAnnotation(Column.class);

        if (an.columnType() == ColumnType.ENCRYPTED) {
          Authentication auth = new Authentication();
          String coded = auth.encode((String) field.get(model));

          field.set(model, coded);
        }
      }
    }
  }

  String getId();
  DBRecord buildRecord();
  Table getTable();
  String toString();
}
