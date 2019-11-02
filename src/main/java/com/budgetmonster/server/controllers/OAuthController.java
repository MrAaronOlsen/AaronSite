package com.budgetmonster.server.controllers;

import com.budgetmonster.response.Response;
import com.budgetmonster.response.ResponseBuilder;
import com.budgetmonster.utils.io.Logger;
import org.bson.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.budgetmonster.server.controllers.MasterController.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class OAuthController extends MasterController {

  @PostMapping("/oathredirect")
  public void deleteOnTableById(
      @RequestHeader Map<String, String> headers,
      @RequestParam Map<String, String> params) {

    try {
      Document paramsDoc = new Document();
      paramsDoc.putAll(params);

      Logger.out(paramsDoc.toJson());
    } catch (Throwable e) {
      Logger.out(e.getMessage());
    }
  }
}
