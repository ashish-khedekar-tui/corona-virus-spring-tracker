package co.uk.coronavirus.services.impl;

import co.uk.coronavirus.generated.CoronaVirusSummary;
import co.uk.coronavirus.generated.Country;
import co.uk.coronavirus.models.LocationStats;
import co.uk.coronavirus.services.CoronaVirusSummaryService;
import co.uk.coronavirus.services.CoronaVirusTrackerService;
import co.uk.coronavirus.services.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service (value = "postmanCoronaVirusTrackerService")
public class PostmanCoronaVirusTrackerService implements CoronaVirusTrackerService
{
   private static final Logger LOG = LoggerFactory.getLogger(PostmanCoronaVirusTrackerService.class);

   @Autowired
   private CountryService countryService;

   @Autowired
   private CoronaVirusSummaryService coronaVirusSummaryService;

   @PostConstruct
   @Scheduled(cron = "* * 1 * * *")
   @Override
   public void fetchStats() throws IOException, InterruptedException
   {
      final CoronaVirusSummary coronaVirusSummary = coronaVirusSummaryService.getCoronaVirusSummary();



   }

   @Override
   public CoronaVirusSummary getCoronaVirusSummary() throws IOException, InterruptedException
   {
      return coronaVirusSummaryService.getCoronaVirusSummary();
   }
}
