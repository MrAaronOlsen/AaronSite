package com.aaronsite.server.controllers;

import com.aaronsite.response.Response;
import com.aaronsite.response.ResponseBuilder;
import com.aaronsite.utils.enums.RequestType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.aaronsite.server.controllers.MasterController.BASE_URL;

@CrossOrigin(origins = "https://aaron-site.herokuapp.com, http://localhost:3000")
@RestController
@RequestMapping(BASE_URL)
public class UpdateController extends MasterController {

  @PutMapping("/{table}/{id}")
  public Response updateOnTableById(
      @RequestHeader Map<String, String> headers,
      @PathVariable(value = "table") String table,
      @PathVariable(value = "id") String id,
      @RequestBody String body) {

    try {
      return new ResponseBuilder(RequestType.UPDATE_BY_ID)
          .setTable(table)
          .setId(id)
          .setBody(body).build();

    } catch (Throwable e) {
      return ResponseBuilder.handleError(e);
    }
  }
}
