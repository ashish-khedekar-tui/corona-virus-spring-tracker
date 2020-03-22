package co.uk.coronavirus;

import co.uk.coronavirus.models.LocationStats;
import co.uk.coronavirus.services.CoronaVirusTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Set;

@Controller
public class HomeController
{



   @Autowired
   private CoronaVirusTrackerService coronaVirusTrackerService;

   @Autowired
   private Environment environment;


   @GetMapping("/")
   public String home(final Model model) throws UnknownHostException
   {
      final Set<LocationStats> locationStatsList = coronaVirusTrackerService.getLocationStatsList();
      model.addAttribute("locationStats", locationStatsList);

      model.addAttribute("hostAddress", InetAddress.getLocalHost().getHostAddress());


      final Optional<LocationStats> first = locationStatsList.stream().findFirst();

      first.ifPresent(e ->
               model.addAttribute("worstAffectedArea", String.format("%s - %s = Total cases reported yesterday - %d ", e.getState(), e.getCountry(), e.getCasesReportedInLastDay())));

      return "home";
   }

   public CoronaVirusTrackerService getCoronaVirusTrackerService()
   {
      return coronaVirusTrackerService;
   }

   public void setCoronaVirusTrackerService(CoronaVirusTrackerService coronaVirusTrackerService)
   {
      this.coronaVirusTrackerService = coronaVirusTrackerService;
   }
}
