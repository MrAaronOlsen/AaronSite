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
import static com.aaronsite.utils.exceptions.ModelException.Code.MODEL_PROCESSING_ERROR;

public interface Model extends ResponseData {

  static Function<DBRecord, Model> getModel(Table table) throws ABException {
    switch (table) {
      case PAGES: return Page::new;
      case USERS: return User::new;
      default:
        throw new ModelException(INVALID_MODEL_TABLE, table.getName());
    }
  }

  static Model deserialize(Model model) throws ModelException {
    Class<?> clazz = model.getClass();

    try {
      for (Field field : clazz.getDeclaredFields()) {
        field.setAccessible(true);

        if (field.isAnnotationPresent(Column.class)) {
          Column an = field.getAnnotation(Column.class);

          if (an.columnType() == ColumnType.ENCRYPTED) {
            String coded = Authentication.encode((String) field.get(model));
            field.set(model, coded);
          }
        }
      }
    } catch (IllegalAccessException e) {
      throw new ModelException(MODEL_PROCESSING_ERROR, e.getMessage());
    }

    return model;
  }

  static Model serialize(Model model) throws ModelException {
    Class<?> clazz = model.getClass();

    try {
      for (Field field : clazz.getDeclaredFields()) {
        field.setAccessible(true);

        if (field.isAnnotationPresent(Column.class)) {
          Column an = field.getAnnotation(Column.class);

          if (an.columnType() == ColumnType.ENCRYPTED) {
            field.set(model, "*****");
          }
        }
      }
    } catch (IllegalAccessException e) {
      throw new ModelException(MODEL_PROCESSING_ERROR, e.getMessage());
    }

    return model;
  }

  String getId();
  DBRecord buildRecord();
  Table getTable();
  String toString();
}
