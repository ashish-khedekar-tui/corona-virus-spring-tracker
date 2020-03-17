package co.uk.coronavirus.services;

import co.uk.coronavirus.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class CoronaVirusTrackerService
{

   final static Logger LOG = LoggerFactory.getLogger(CoronaVirusTrackerService.class);

   private static final String CORONA_VIRUS_CONFIRMED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

   private Set<LocationStats> locationStatsList = new TreeSet<>();

   @PostConstruct
   @Scheduled(cron = "* * 1 * * *")
   public void fetchStats() throws IOException, InterruptedException
   {

      LOG.info("Fetching stats... ");

      final HttpClient httpClient = HttpClient.newBuilder().build();

      final HttpRequest httpRequest = HttpRequest.newBuilder()
               .uri(URI.create(CORONA_VIRUS_CONFIRMED_URL))
               .build();

      final HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

      final StringReader stringReader = new StringReader(httpResponse.body());
      Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
      for (final CSVRecord record : records)
      {
         final LocationStats locationStats = new LocationStats();
         final String country = record.get("Country/Region");
         locationStats.setCountry(country);
         final String state = record.get("Province/State");
         locationStats.setState(state != null && !state.equalsIgnoreCase("") ? state : country);
         locationStats.setTotalReportedCases(Integer.parseInt(record.get(record.size() - 1)));

         final int casesReportedInLastWeek = Integer.parseInt(record.get(record.size() - 1)) - Integer.parseInt(record.get(record.size() - 7));
         locationStats.setCasesReportedInLastWeek(casesReportedInLastWeek);

         final int casesReportedInLastDay = Integer.parseInt(record.get(record.size() - 1)) - Integer.parseInt(record.get(record.size() - 2));
         locationStats.setCasesReportedInLastDay(casesReportedInLastDay);
         locationStatsList.add(locationStats);
      }
   }

   public Set<LocationStats> getLocationStatsList()
   {
      return locationStatsList;
   }
}
