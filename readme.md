![](https://rawgit.com/sidduramesh/reverse-geo-coding/master/src/main/resources/static/icon_geocoding.png)

# Reverse Geocoding API

Reverse Geocdding API is a REST API that is designed to use third part Map services like google to provide navigable address based on cordinates i.e `longitude` and `latitude`. The REST API exposes two endpoints 

## API
- GET /api/address - This operation expects two query paramater i.e longitude and latitude to resolve it to an address.
- GET /api/previous-addresses - This operation provides the last 10 resolved address. At the momemnt the number is not configurable and it provides only the latest 10.

## Installation

The project can be built using below maven command
```bash
$ cd reverse-geo-coding
$ mvn clean install -Dapikey=AIzaSyDejOYu1YiMMBHXRCVnUK1Fh5nyOUrbj2c
```

Once the springboot artfact is built, the project can run using embedded tomcat. To start the application use the below commands
```bash
$ cd reverse-geo-coding/target
$ java -Dapikey=AIzaSyDejOYu1YiMMBHXRCVnUK1Fh5nyOUrbj2c -jar reverse-address-lookup.jar
```

## Documentation
The REST API is well documented using Swagger UI. This provides developer/analyst intrested in service integration or just want to try out the service

Swagger Docs: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

![](https://rawgit.com/sidduramesh/reverse-geo-coding/master/src/main/resources/static/swagger-ui.png)

## See also:
- Google API - https://developers.google.com/maps/documentation/geocoding/intro#ReverseGeocoding
- Swagger - http://swagger.io/

