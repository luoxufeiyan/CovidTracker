package au.edu.unsw.infs3634.covidtracker;

import androidx.room.Database;

@Database(entities = {Country.class},version = 1)

public abstract class CountryDatabase extends RoomDatabase {
    public abstract CountryDao countryDao();
}
