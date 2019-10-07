package com.budgetmonster.models;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBResult;
import com.budgetmonster.database.operations.DbQuery;
import com.budgetmonster.utils.annotations.Column;
import com.budgetmonster.utils.enums.ColumnType;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.SimpleMessageException;
import com.budgetmonster.utils.io.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ModelBuilder {

  public static void build(Model model) throws ABException {
    Field[] fields = model.getClass().getDeclaredFields();

    Map<String, Object> map = new HashMap<>();

    for (Field field : fields) {
      field.setAccessible(true);
      Column column = field.getAnnotation(Column.class);

      if (column == null) {
        continue;
      }

      try {
        if (column.type() == ColumnType.TABLE_LOOKUP) {
            Table table = column.table();
            String id = (String) field.get(model);

            try (DBConnection dbConn = new DBConnection()) {
              DbQuery query = new DbQuery(dbConn, table).setIdQuery(id);
              DBResult result = query.execute();

              if (result.hasNext()) {
                map.put(field.getName(), result.getNext());
              }
            }
        } else if (!Modifier.isStatic(field.getModifiers())) {
          String value = (String) field.get(model);
          map.put(field.getName(), value);
        }
      } catch (IllegalAccessException e) {
        throw new SimpleMessageException("Failed to convert value of field " + field.getName());
      }

      ObjectMapper mapper = new ObjectMapper();
      try {
        Logger.out(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map), Logger.Color.ANSI_BLUE);
      } catch (IOException e) {
        throw new SimpleMessageException("Failed to convert map to json.");
      }
    }
  }
}
