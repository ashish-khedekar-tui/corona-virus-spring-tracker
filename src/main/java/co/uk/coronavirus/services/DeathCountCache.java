package co.uk.coronavirus.services;

import co.uk.coronavirus.generated.CoronaVirusSummary;
import co.uk.coronavirus.generated.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DeathCountCache
{
   public static final Logger LOG = LoggerFactory.getLogger(DeathCountCache.class);

   public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-mm-yyyy");

   @Autowired
   private CoronaVirusSummaryService coronaVirusSummaryService;

   private Map<String, CoronaVirusSummary> deathCountMap = new HashMap<>();

   public int getDeathCountFor(final String countrySlug) throws IOException, InterruptedException
   {
      LOG.debug("Looking up deaths for country slug [{}]" , countrySlug);
      final String today = SIMPLE_DATE_FORMAT.format(new Date());
      final CoronaVirusSummary deathCountForCountry = deathCountMap.get(today);

      if (deathCountForCountry != null)
      {
         LOG.debug("Cache already built, returning from cache");
         return findDeaths(countrySlug, deathCountForCountry);
      }
      else
      {
         LOG.debug("First request for the day [{}] so building the cache", today);
         final CoronaVirusSummary coronaVirusSummary = coronaVirusSummaryService.getCoronaVirusSummary();
         deathCountMap.put(today, coronaVirusSummary);
         return findDeaths(countrySlug, coronaVirusSummary);
      }
   }

   private int findDeaths(final String country, CoronaVirusSummary deathCountForCountry)
   {
      final Optional<Country> countryDetails = deathCountForCountry.getCountries().stream()
               .filter(countryItr -> countryItr.getSlug().equalsIgnoreCase(country))
               .findFirst();

      return countryDetails.isPresent() ? countryDetails.get().getTotalDeaths() : 0;
   }

}
