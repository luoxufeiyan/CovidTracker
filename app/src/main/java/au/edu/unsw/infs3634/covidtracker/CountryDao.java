package au.edu.unsw.infs3634.covidtracker;

import java.util.List;


import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Insert;


@Dao
public interface CountryDao {
    @Query("SELECT * FROM country")
    List<Country> getCountries();

    @Query("Select * FROM country Where countryCode == :countryCode");
    country getCountry (String countryCode);

    @Insert
    void insertAll(Country...countries);

    @Delete
    void deleteAll(Country...countries);
}