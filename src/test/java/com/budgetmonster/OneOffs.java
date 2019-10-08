package com.budgetmonster;

import com.budgetmonster.server.TestServer;
import com.budgetmonster.testutils.dataseeder.MonthSeeder;
import com.budgetmonster.utils.exceptions.ABException;
import org.junit.jupiter.api.Test;

class OneOffs extends TestServer {

  @Test
  void testAThing() throws ABException {
    new MonthSeeder().execute();
  }
}
