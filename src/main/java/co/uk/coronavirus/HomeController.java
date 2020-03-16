package co.uk.coronavirus;

import co.uk.coronavirus.services.CoronaVirusTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{

   @Autowired
   private CoronaVirusTrackerService coronaVirusTrackerService;


   @GetMapping("/")
   public String home(final Model model)
   {
      model.addAttribute("locationStats", coronaVirusTrackerService.getLocationStatsList());
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
