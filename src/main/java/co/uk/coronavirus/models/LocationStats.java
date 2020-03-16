package co.uk.coronavirus.models;

public class LocationStats implements Comparable<LocationStats>
{
   private String state;

   private String country;

   private int totalReportedCases;

   private int casesReportedInLastWeek;

   private int casesReportedInLastDay;

   public String getState()
   {
      return state;
   }

   public void setState(String state)
   {
      this.state = state;
   }

   public String getCountry()
   {
      return country;
   }

   public void setCountry(String country)
   {
      this.country = country;
   }

   public int getTotalReportedCases()
   {
      return totalReportedCases;
   }

   public void setTotalReportedCases(int totalReportedCases)
   {
      this.totalReportedCases = totalReportedCases;
   }

   public int getCasesReportedInLastWeek()
   {
      return casesReportedInLastWeek;
   }

   public void setCasesReportedInLastWeek(int casesReportedInLastWeek)
   {
      this.casesReportedInLastWeek = casesReportedInLastWeek;
   }

   public int getCasesReportedInLastDay()
   {
      return casesReportedInLastDay;
   }

   public void setCasesReportedInLastDay(int casesReportedInLastDay)
   {
      this.casesReportedInLastDay = casesReportedInLastDay;
   }

   @Override
   public int compareTo(final LocationStats that)
   {
      return that.getCasesReportedInLastDay() - this.getCasesReportedInLastDay();
   }
}
