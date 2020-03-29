package co.uk.coronavirus;

import co.uk.coronavirus.comparators.ComparisonCriteria;
import co.uk.coronavirus.comparators.CountryComparator;
import co.uk.coronavirus.generated.CoronaVirusSummary;
import co.uk.coronavirus.services.CoronaVirusTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Controller
public class HomeController
{
   @Autowired
   @Qualifier (value = "postmanCoronaVirusTrackerService")
   private CoronaVirusTrackerService coronaVirusTrackerService;

   @Autowired
   private Environment environment;

   @GetMapping("/")
   public String home(final Model model) throws IOException, InterruptedException, ParseException
   {
      final CoronaVirusSummary coronaVirusSummary = getCoronaVirusTrackerService().getCoronaVirusSummary();
      model.addAttribute("coronaVirusSummary", coronaVirusSummary);

      coronaVirusSummary.getCountries()
               .stream().max(new CountryComparator(ComparisonCriteria.TOTAL_DEATHS))
               .ifPresent(country -> model.addAttribute("maxDeaths", String.format("Max Total Deaths - %s - %s ", country.getCountry(), country.getTotalDeaths())));

      coronaVirusSummary.getCountries()
               .stream().max(new CountryComparator(ComparisonCriteria.TOTAL_CONFIRMED))
               .ifPresent(country -> model.addAttribute("maxConfirmed", String.format("Max Total Confirmed Cases - %s - %s ", country.getCountry(), country.getTotalConfirmed())));


      coronaVirusSummary.getCountries()
               .stream().max(new CountryComparator(ComparisonCriteria.NEW_DEATHS))
               .ifPresent(country -> model.addAttribute("newDeaths", String.format("Max New Deaths - %s - %s ", country.getCountry(), country.getNewDeaths())));

      coronaVirusSummary.getCountries()
               .stream().max(new CountryComparator(ComparisonCriteria.NEW_CONFIRMED))
               .ifPresent(country -> model.addAttribute("newConfirmed", String.format("Max New Confirmed - %s - %s ", country.getCountry(), country.getNewConfirmed())));

      //2020-03-29T02:07:26.174102617Z
      final SimpleDateFormat simpleDateFormatOriginal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      final SimpleDateFormat simpleDateFormatFormatted = new SimpleDateFormat("dd-MM-yyyy");
      model.addAttribute("lastUpdatedTime", simpleDateFormatFormatted.format(simpleDateFormatOriginal.parse(coronaVirusSummary.getDate())));

      model.addAttribute("hostAddress", InetAddress.getLocalHost().getHostAddress());

      return "home";
   }

   public CoronaVirusTrackerService getCoronaVirusTrackerService()
   {
      return coronaVirusTrackerService;
   }
}
