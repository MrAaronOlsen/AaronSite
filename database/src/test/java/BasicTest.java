import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class BasicTest {
  private static final String URL = "jdbc:postgresql://localhost:5432/budgetmonster";
  private static final String USER = "wolverine";
  private static final String PASSWORD = "Cuntmouth1021@";

  @Test
  void aBasicTest() throws SQLException {
    Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
    Statement stmt = conn.createStatement();
    ResultSet resultSet = stmt.executeQuery("SELECT * FROM test.budgets");

    while (resultSet.next()) {
      System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
    }
  }
}
