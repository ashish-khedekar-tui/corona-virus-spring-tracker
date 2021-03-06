package co.uk.coronavirus.services;

import co.uk.coronavirus.generated.CoronaVirusSummary;

import java.io.IOException;

public interface CoronaVirusTrackerService
{
   void fetchStats() throws IOException, InterruptedException;

   CoronaVirusSummary getCoronaVirusSummary() throws IOException, InterruptedException;
}
