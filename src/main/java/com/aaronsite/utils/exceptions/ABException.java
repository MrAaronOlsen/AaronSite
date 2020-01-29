package com.aaronsite.utils.exceptions;

import com.aaronsite.utils.io.Logger;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class ABException extends Exception {
  private ExceptionCode code;
  private String[] args;

  public ABException() {
    // Default Constructor
  }

  public ABException(ExceptionCode code, String[] args) {
    this.code = code;
    this.args = args;
  }

  public void setCode(ExceptionCode code) {
    this.code = code;
  }

  public ExceptionCode getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    String codeMessage = code.getMessage();
    int argReq = StringUtils.countMatches("%s", codeMessage);

    if (args.length != argReq) {
      Logger.err("Argument mismatch for error deserialization. Code: " + code.getName() + ". Expected " + argReq + " but found only " + args.length);
    }
    return String.format(code.getMessage(), args);
  }

  @Override
  public String toString() {
    return "ABException{" +
        "code=" + code +
        ", args=" + Arrays.toString(args) +
        '}';
  }
}
