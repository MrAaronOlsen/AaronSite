package com.aaronsite.server.controllers;

import com.aaronsite.response.Response;
import com.aaronsite.response.ResponseBuilder;
import com.aaronsite.security.Authentication;
import com.aaronsite.utils.enums.RequestType;
import com.aaronsite.utils.exceptions.AuthException;
import com.aaronsite.utils.io.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.Map;

import static com.aaronsite.server.controllers.MasterController.BASE_URL;
import static com.aaronsite.server.controllers.MasterController.DEV_URL;
import static com.aaronsite.server.controllers.MasterController.PROD_URL_A;
import static com.aaronsite.server.controllers.MasterController.PROD_URL_B;
import static com.aaronsite.utils.enums.Role.READ;

@CrossOrigin(origins = {DEV_URL, PROD_URL_A, PROD_URL_B})
@RestController
@RequestMapping(BASE_URL)
public class QueryController extends MasterController {

  @GetMapping("/{table}")
  public ResponseEntity<Response> queryOnTable(
      @RequestHeader("Authorization") String authHeader,
      @PathVariable(value = "table") String table,
      @RequestParam Map<String, String> params) {

    try {
      try {
        Authentication.authenticate(authHeader, EnumSet.of(READ));
      } catch (AuthException e) {
        return new ResponseEntity<>(ResponseBuilder.handleError(e), HttpStatus.FORBIDDEN);
      }

      return new ResponseEntity<>(new ResponseBuilder(RequestType.QUERY)
          .setTable(table)
          .setParams(params).build(), HttpStatus.OK);

    } catch (Throwable e) {
      return new ResponseEntity<>(ResponseBuilder.handleError(e), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/{table}/{id}")
  public ResponseEntity<Response> queryOnTableById(
      @RequestHeader("Authorization") String authHeader,
      @PathVariable(value = "table") String table,
      @PathVariable(value = "id") String id,
      @RequestParam Map<String, String> params) {

    try {
      try {
        Authentication.authenticate(authHeader, EnumSet.of(READ));
      } catch (AuthException e) {
        Logger.out("Error: " + e.getMessage());
        return new ResponseEntity<>(ResponseBuilder.handleError(e), HttpStatus.FORBIDDEN);
      }

      return new ResponseEntity<>(new ResponseBuilder(RequestType.QUERY_BY_ID)
          .setTable(table)
          .setId(id)
          .setParams(params).build(), HttpStatus.OK);

    } catch (Throwable e) {
      Logger.out("Error: " + e.getMessage());
      return new ResponseEntity<>(ResponseBuilder.handleError(e), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/keepawake")
  public String keepAwake() {
    return "I'm awake!";
  }
}
