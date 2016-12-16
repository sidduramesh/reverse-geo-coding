package com.geoloc;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class GeolocationApplication {

  public static void main(String[] args) {
    SpringApplication.run(GeolocationApplication.class, args);
  }

  @Bean
  public Docket newsApi() {
    return new Docket(DocumentationType.SWAGGER_2).groupName("ReverseGeoCodeLookup")
        .apiInfo(apiInfo()).select().paths(regex("/api.*")).build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Reverse Geocode Lookup Service")
        .description(
            "The RESTful service is designed to provide reverse geo code lookup by accepting  longitude and latitude as input and return address as return. The Service also provides an api to look up the last 10 address.")
        .contact("Ramesha Siddegowda").version("1.0").build();
  }
}
