package com.aaronsite.server.controllers;

import com.aaronsite.response.ErrorResponse;
import com.aaronsite.response.ResponseData;
import com.aaronsite.security.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.aaronsite.server.controllers.MasterController.BASE_URL;

@CrossOrigin(origins = {"http://localhost:3000", "https://aaron-site.herokuapp.com"})
@RestController
@RequestMapping(BASE_URL)
public class AuthController {

  @PostMapping("/gettoken")
  public ResponseData getToken(
      @RequestHeader("Authorization") String authHeader) {

    try {
      return Authentication.basicAuth(authHeader);
    } catch (Throwable e) {
      return new ErrorResponse(e);
    }
  }
}
