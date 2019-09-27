import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AllTests {

  @Test
  public void thatWeCanEvenTest() {
    Assertions.assertTrue(StringUtils.equals("Hello", "Hello"));
  }
}