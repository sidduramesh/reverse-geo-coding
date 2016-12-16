package com.geoloc.utils;

import org.springframework.http.HttpStatus;

/**
 * Created by Ramesha Siddegowda on 12/13/16.
 */
public class GeoLocationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private HttpStatus errCode;
  private String errMsg;

  public HttpStatus getErrCode() {
    return errCode;
  }

  public void setErrCode(HttpStatus errCode) {
    this.errCode = errCode;
  }

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

  public GeoLocationException(String errMsg, HttpStatus errCode) {
    this.errCode = errCode;
    this.errMsg = errMsg;
  }

}
