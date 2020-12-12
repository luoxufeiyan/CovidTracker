package au.edu.unsw.infs3634.covidtracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {
    @Query("SELECT * FROM country")
    List<Country> getCountries();

    @Query("SELECT * FROM country WHERE countryCode == :countryCode")
    Country getCountry(String countryCode);

    @Insert
    void insertAll(Country... countries);

    @Delete
    void deleteAll(Country... countries);
}
