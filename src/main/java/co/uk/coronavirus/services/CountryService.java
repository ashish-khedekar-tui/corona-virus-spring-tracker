package co.uk.coronavirus.services;

import co.uk.coronavirus.generated.Country;

import java.io.IOException;
import java.util.List;

public interface CountryService
{
   List<Country> getCountries() throws IOException, InterruptedException;

}
