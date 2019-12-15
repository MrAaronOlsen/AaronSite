package com.aaronsite.server.controllers;

import com.aaronsite.response.Response;
import com.aaronsite.response.ResponseBuilder;
import com.aaronsite.security.Authentication;
import com.aaronsite.utils.enums.RequestType;
import com.aaronsite.utils.exceptions.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;

import static com.aaronsite.server.controllers.MasterController.BASE_URL;
import static com.aaronsite.server.controllers.MasterController.DEV_URL;
import static com.aaronsite.server.controllers.MasterController.PROD_URL_A;
import static com.aaronsite.server.controllers.MasterController.PROD_URL_B;
import static com.aaronsite.utils.enums.Role.DELETE;

@CrossOrigin(origins = {DEV_URL, PROD_URL_A, PROD_URL_B})
@RestController
@RequestMapping(BASE_URL)
public class DeleteController extends MasterController {

  @DeleteMapping("/{table}/{id}")
  public ResponseEntity<Response> deleteOnTableById(
      @RequestHeader("Authorization") String authHeader,
      @PathVariable(value = "table") String table,
      @PathVariable(value = "id") String id) {

    try {
      try {
        Authentication.authenticate(authHeader, EnumSet.of(DELETE));
      } catch (AuthException e) {
        return new ResponseEntity<>(ResponseBuilder.handleError(e), HttpStatus.FORBIDDEN);
      }

      return new ResponseEntity<>(new ResponseBuilder(RequestType.DELETE_BY_ID)
          .setTable(table)
          .setId(id).build(), HttpStatus.OK);

    } catch (Throwable e) {
      return new ResponseEntity<>(ResponseBuilder.handleError(e), HttpStatus.BAD_REQUEST);
    }
  }
}
