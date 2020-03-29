package co.uk.coronavirus.services.impl;

import co.uk.coronavirus.generated.CoronaVirusSummary;
import co.uk.coronavirus.generated.Country;
import co.uk.coronavirus.services.CoronaVirusSummaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostmanCoronaVirusSummaryService implements CoronaVirusSummaryService
{
   private static final Logger LOG = LoggerFactory.getLogger(PostmanCoronaVirusSummaryService.class);

   public static final String POSTMAN_CORONA_VIRUS_BASE_URL = "https://api.covid19api.com";

   public static final String POSTMAN_CORONA_VIRUS_GET_SUMMARY_URL = POSTMAN_CORONA_VIRUS_BASE_URL + "/summary";

   public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

   @Override
   public CoronaVirusSummary getCoronaVirusSummary() throws IOException, InterruptedException
   {
      final HttpClient httpClient = HttpClient.newBuilder().build();

      final HttpRequest httpRequest = HttpRequest.newBuilder()
               .uri(URI.create(POSTMAN_CORONA_VIRUS_GET_SUMMARY_URL))
               .build();

      final String getCoronaVirusSummaryResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
               .body();

      final CoronaVirusSummary coronaVirusSummary = OBJECT_MAPPER.readValue(getCoronaVirusSummaryResponse, CoronaVirusSummary.class);

      coronaVirusSummary.getCountries()
               .removeIf(country -> country.getCountry() == null || country.getCountry().equalsIgnoreCase(""));

      return coronaVirusSummary;
   }
}
