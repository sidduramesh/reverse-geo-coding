package com.geoloc.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geoloc.domain.ResolvedAddress;
import com.geoloc.utils.GeoLocationException;

/**
 * Created by Ramesha Siddegowda on 12/13/16.
 */
@Service
public class ReverseGeoLocationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReverseGeoLocationService.class);

  private LinkedList<ResolvedAddress> addressList = new LinkedList<ResolvedAddress>();

  private void resolvePreviousAddresses(ResolvedAddress address) {
    if (addressList.size() == 10) {
      addressList.removeFirst();
    }
    addressList.add(address);
  }

  public Optional<ResolvedAddress> getAddressForGeoLocation(final String longitude,
      final String latitude) {
    ResolvedAddress resolvedAddress;
    try {
      LOGGER.info("About to resolve address for coordinates: " + longitude, latitude);
      if (fetchAddress(longitude, latitude) != null) {
        if (fetchAddress(longitude, latitude).findValue("formatted_address") != null) {
          String formattedAddress =
              fetchAddress(longitude, latitude).findValue("formatted_address").asText();
          if (null != formattedAddress) {
            LOGGER.info("Formatted Address: " + formattedAddress);
            resolvedAddress = new ResolvedAddress();
            resolvedAddress.setLatitude(latitude);
            resolvedAddress.setLongitude(longitude);
            resolvedAddress.setResolvedAddress(formattedAddress);
            resolvedAddress.setResolvedTimestamp(
                new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date()));
            resolvePreviousAddresses(resolvedAddress);
            return Optional.of(resolvedAddress);
          }
        }
      }
    } catch (IOException e) {
      throw new GeoLocationException("Unable to get valid address for given coordinates",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return Optional.empty();
  }


  private static JsonNode fetchAddress(final String longitude, final String latitude)
      throws IOException {

    /*
     * FIXME: It is a better solution to externalize the Google Map service URL and API key. Either
     * as a property that can be injected during service initialization.
     */

    String APIK_KEY = System.getProperty("apikey");
    String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    String geoUrl = new StringBuilder(url).append(latitude).append(",").append(longitude)
        .append("&key=" + APIK_KEY).toString();
    LOGGER.info("Google MAP API url: " + geoUrl);
    final RestTemplate restTemplate = new RestTemplate();
    final String geoResponse = restTemplate.getForObject(geoUrl, String.class);
    LOGGER.info(geoResponse);
    final ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readTree(geoResponse);
  }

  public Optional<List<ResolvedAddress>> getCachedAddresses() {
    if (!addressList.isEmpty()) {
      return Optional.of(addressList);
    }
    return Optional.empty();
  }
}
