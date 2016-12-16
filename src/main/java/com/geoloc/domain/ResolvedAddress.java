package com.geoloc.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Ramesha Siddegowda on 12/14/16.
 */
@ApiModel
public class ResolvedAddress {
  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  @ApiModelProperty(value = "Recorded longitude value from the request",
      allowableValues = "-84.100033", required = true)
  private String longitude;

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getResolvedTimestamp() {
    return dateCreated;
  }

  public void setResolvedTimestamp(String resolvedTimestamp) {
    this.dateCreated = resolvedTimestamp;
  }

  public String getResolvedAddress() {
    return resolvedAddress;
  }

  public void setResolvedAddress(String resolvedAddress) {
    this.resolvedAddress = resolvedAddress;
  }

  @ApiModelProperty(value = "Latitude value recorded from the request",
      allowableValues = "33.969601", required = true)
  private String latitude;

  @ApiModelProperty(
      value = "Date/timestamp that gets created when address is resolved from reverse geocode lookup service",
      allowableValues = "12-14-2016 17:24:07", required = true)
  private String dateCreated;

  @ApiModelProperty(
      value = "Formatted address which is resolved through reverse geocdode lookup service",
      allowableValues = "2651 Satellite Blvd, Duluth, GA 30096, USA", required = true)
  private String resolvedAddress;


}
