package com.ifykyk.exception;

public class LalinguaBussinessException extends RuntimeException {

  public LalinguaBussinessException(String msg) {
    super(msg);
  }

  public LalinguaBussinessException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
