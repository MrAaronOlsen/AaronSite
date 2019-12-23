package com.aaronsite.server.controllers;

import com.aaronsite.response.Response;
import com.aaronsite.response.ResponseBuilder;
import com.aaronsite.security.Authentication;
import com.aaronsite.utils.enums.RequestType;
import com.aaronsite.utils.exceptions.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;

import static com.aaronsite.server.controllers.MasterController.BASE_URL;
import static com.aaronsite.server.controllers.MasterController.DEV_URL;
import static com.aaronsite.server.controllers.MasterController.PROD_URL_A;
import static com.aaronsite.server.controllers.MasterController.PROD_URL_B;
import static com.aaronsite.utils.enums.Role.INSERT;

@CrossOrigin(origins = {DEV_URL, PROD_URL_A, PROD_URL_B})
@RestController
@RequestMapping(BASE_URL)
public class ActionController extends MasterController {

  @PostMapping("/action")
  public ResponseEntity<Response> executeAction(
      @RequestHeader("Authorization") String authHeader,
      @RequestBody String body) {

    try {
      try {
        Authentication.authenticate(authHeader, EnumSet.of(INSERT));
      } catch (AuthException e) {
        return new ResponseEntity<>(ResponseBuilder.handleError(e), HttpStatus.FORBIDDEN);
      }

      return new ResponseEntity<>(new ResponseBuilder(RequestType.ACTION)
          .setBody(body)
          .build(), HttpStatus.OK);

    } catch (Throwable e) {
      return new ResponseEntity<>(ResponseBuilder.handleError(e), HttpStatus.BAD_REQUEST);
    }
  }
}
