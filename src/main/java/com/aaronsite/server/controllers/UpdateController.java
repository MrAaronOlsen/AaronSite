package com.aaronsite.server.controllers;

import com.aaronsite.response.Response;
import com.aaronsite.response.ResponseBuilder;
import com.aaronsite.security.Authentication;
import com.aaronsite.utils.enums.RequestType;
import com.aaronsite.utils.exceptions.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;

import static com.aaronsite.server.controllers.MasterController.BASE_URL;
import static com.aaronsite.server.controllers.MasterController.DEV_URL;
import static com.aaronsite.server.controllers.MasterController.PROD_URL_A;
import static com.aaronsite.server.controllers.MasterController.PROD_URL_B;
import static com.aaronsite.utils.enums.Role.UPDATE;

@CrossOrigin(origins = {DEV_URL, PROD_URL_A, PROD_URL_B})
@RestController
@RequestMapping(BASE_URL)
public class UpdateController extends MasterController {

  @PutMapping("/{table}/{id}")
  public ResponseEntity<Response> updateOnTableById(
      @RequestHeader("Authorization") String authHeader,
      @PathVariable(value = "table") String table,
      @PathVariable(value = "id") String id,
      @RequestBody String body) {

    try {
      try {
        Authentication.authenticate(authHeader, EnumSet.of(UPDATE));
      } catch (AuthException e) {
        return new ResponseEntity<>(ResponseBuilder.handleError(e), HttpStatus.FORBIDDEN);
      }

      return new ResponseEntity<>(new ResponseBuilder(RequestType.UPDATE_BY_ID)
          .setTable(table)
          .setId(id)
          .setBody(body).build(), HttpStatus.OK);

    } catch (Throwable e) {
      return new ResponseEntity<>(ResponseBuilder.handleError(e), HttpStatus.BAD_REQUEST);
    }
  }
}
