package com.geoloc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.geoloc.domain.ResolvedAddress;

/**
 * Created by Ramesha Siddegowda on 12/14/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ReverseGeoLocationService.class)
public class ReverseGeoLocationServiceTest {

    ReverseGeoLocationService service;

    @Before
    public void before() {
        service = new ReverseGeoLocationService();
    }


    @Test
    public void getAddressForGeoLocationTest() {

        Optional<ResolvedAddress> address = service.getAddressForGeoLocation("-84.100033", "33.969601");
        assertNotNull(address.get());
    }

    @Test
    public void getAddressForGeoLocationWithWrongStringTest() {
        Optional<ResolvedAddress> address = service.getAddressForGeoLocation("str1", "str2");
        assertEquals(Optional.empty(), address);

    }

    @Test
    public void getAddressGeolOcationForEmptyCoordinateValues() {
        Optional<ResolvedAddress> address = service.getAddressForGeoLocation("", "");
        assertEquals(Optional.empty(), address);
    }

    @Test
    public void getCachedAddressesForValidValues() {
        Optional<ResolvedAddress> address = service.getAddressForGeoLocation("-84.100033", "33.969601");
        Optional<List<ResolvedAddress>> addressses = service.getCachedAddresses();
        assertEquals(addressses.get().size(), 1);
    }

    @Test
    public void getCachedAddressesForEmptyValues() {
        Optional<ResolvedAddress> address = service.getAddressForGeoLocation("str1", "str2");
        Optional<List<ResolvedAddress>> addressses = service.getCachedAddresses();
        assertEquals(addressses, Optional.empty());
    }
}
