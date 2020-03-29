package co.uk.coronavirus.services.impl;

import co.uk.coronavirus.generated.Country;
import co.uk.coronavirus.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class PostmanCountryService implements CountryService
{
   private static final Logger LOG = LoggerFactory.getLogger(PostmanCountryService.class);

   public static final String POSTMAN_CORONA_VIRUS_BASE_URL = "https://api.covid19api.com";

   public static final String POSTMAN_CORONA_VIRUS_GET_COUNTRIES_URL = POSTMAN_CORONA_VIRUS_BASE_URL + "/countries";

   public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

   @Override
   public List<Country> getCountries() throws IOException, InterruptedException
   {
      final HttpClient httpClient = HttpClient.newBuilder().build();

      final HttpRequest httpRequest = HttpRequest.newBuilder()
               .uri(URI.create(POSTMAN_CORONA_VIRUS_GET_COUNTRIES_URL))
               .build();

      final String getCountriesResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
               .body();

      final Country[] countries = OBJECT_MAPPER.readValue(getCountriesResponse, Country[].class);
      return Arrays.asList(countries);
   }
}
