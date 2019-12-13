package com.aaronsite.server.controllers;

import com.aaronsite.response.Response;
import com.aaronsite.response.ResponseBuilder;
import com.aaronsite.security.Authentication;
import com.aaronsite.utils.enums.RequestType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.aaronsite.server.controllers.MasterController.BASE_URL;
import static com.aaronsite.server.controllers.MasterController.DEV_URL;
import static com.aaronsite.server.controllers.MasterController.PROD_URL_A;
import static com.aaronsite.server.controllers.MasterController.PROD_URL_B;

@CrossOrigin(origins = {DEV_URL, PROD_URL_A, PROD_URL_B})
@RestController
@RequestMapping(BASE_URL)
public class DeleteController extends MasterController {

  @DeleteMapping("/{table}/{id}")
  public Response deleteOnTableById(
      @RequestHeader("Authorization") String authHeader,
      @PathVariable(value = "table") String table,
      @PathVariable(value = "id") String id) {

    try {
      Authentication.authenticate(authHeader);

      return new ResponseBuilder(RequestType.DELETE_BY_ID)
          .setTable(table)
          .setId(id).build();

    } catch (Throwable e) {
      return ResponseBuilder.handleError(e);
    }
  }
}
