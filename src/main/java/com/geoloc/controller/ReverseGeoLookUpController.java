package com.geoloc.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geoloc.domain.ResolvedAddress;
import com.geoloc.service.ReverseGeoLocationService;
import com.geoloc.utils.GeoLocationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;


/**
 * Created by Ramesha Siddegowda on 12/13/16.
 */
@Controller
@RequestMapping("/api")
@Api(value = "Reverse Geocode API")
public class ReverseGeoLookUpController {

  private static final Logger LOGGER =
      org.slf4j.LoggerFactory.getLogger(ReverseGeoLookUpController.class);
  
  private ReverseGeoLocationService reverseGeoLocationService;
  
  @Autowired
  public ReverseGeoLookUpController(ReverseGeoLocationService reverseGeoLocationService){
    this.reverseGeoLocationService = reverseGeoLocationService;
  }

  @RequestMapping(value = "/ping", method = RequestMethod.GET)
  @ApiOperation(value = "Ping")
  @ApiResponse(code = 200, message = "Service is active")
  public ResponseEntity<String> serviceCheck() {
    LOGGER.info("Service is active");
    return new ResponseEntity<String>("Reverse geo lookup service is active", null, HttpStatus.OK);
  }

  @RequestMapping(value = "/address", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ApiOperation(
      value = "Reverse geocode lookup service to resolve coordinates into navigable address")
  @ApiResponse(code = 200, message = "Coordinates are resolved to an address")
  public @ResponseBody ResponseEntity<?> getReverseGeoLocation(
      @RequestParam("longitude") @ApiParam(name = "longitude",
          value = "longitude value of coordinates", required = true) String longitude,
      @ApiParam(name = "latitude", value = "latitude value of coordinates",
          required = true) @RequestParam("latitude") String latitude) {

    if (validateInput(longitude, latitude)) {
      LOGGER.info("Input validation is complete");
      try {
        Optional<ResolvedAddress> address =
            reverseGeoLocationService.getAddressForGeoLocation(longitude, latitude);
        if (address.isPresent()) {
          LOGGER.info("Address is resolved for given coordinates with value: " + address.get());

          return new ResponseEntity<ResolvedAddress>(address.get(), HttpStatus.OK);
        }
      } catch (GeoLocationException e) {
        LOGGER.error("Address cannot be resolved for given coordinates");
        return new ResponseEntity<String>(e.getErrMsg(), e.getErrCode());
      }
    }
    return new ResponseEntity<String>("Enter valid longitude and latitude", HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/previous-addresses", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody ResponseEntity<?> getReverseGeLocation() throws GeoLocationException {

    if (reverseGeoLocationService.getCachedAddresses().isPresent()) {
      return new ResponseEntity<List<ResolvedAddress>>(
          reverseGeoLocationService.getCachedAddresses().get(), HttpStatus.OK);
    }
    return new ResponseEntity<String>("Enter valid longitude and latitude", HttpStatus.BAD_REQUEST);

  }

  private boolean validateInput(String longitude, String latitude) {

    if (!(longitude == null || longitude.isEmpty()) || !(latitude == null || latitude.isEmpty())) {
      return true;
    }
    return false;
  }

}
