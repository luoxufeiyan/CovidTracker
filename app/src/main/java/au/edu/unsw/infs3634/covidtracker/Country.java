package au.edu.unsw.infs3634.covidtracker;

import java.util.ArrayList;

public class Country {

    public Country(String country, String countryCode, String slug, Integer newConfirmed, Integer totalConfirmed, Integer newDeaths, Integer totalDeaths, Integer newRecovered, Integer totalRecovered, String date) {
        this.country = country;
        this.countryCode = countryCode;
        this.slug = slug;
        this.newConfirmed = newConfirmed;
        this.totalConfirmed = totalConfirmed;
        this.newDeaths = newDeaths;
        this.totalDeaths = totalDeaths;
        this.newRecovered = newRecovered;
        this.totalRecovered = totalRecovered;
        this.date = date;
    }

    private String country;
    private String countryCode;
    private String slug;
    private Integer newConfirmed;
    private Integer totalConfirmed;
    private Integer newDeaths;
    private Integer totalDeaths;
    private Integer newRecovered;
    private Integer totalRecovered;
    private String date;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(Integer newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public Integer getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(Integer totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public Integer getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(Integer newDeaths) {
        this.newDeaths = newDeaths;
    }

    public Integer getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(Integer totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public Integer getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(Integer newRecovered) {
        this.newRecovered = newRecovered;
    }

    public Integer getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(Integer totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static ArrayList<Country> getCountries() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("United States of America", "US", "united-states", 44091, 5482416, 1324, 171821, 32579, 1898159, "2020-08-19T08:46:16Z"));
        countries.add(new Country("Brazil","BR","brazil",47784,3407354,1352,109888,52166,2751246,"2020-08-19T08:46:16Z"));
        countries.add(new Country("India","IN","india",64572,2767253,1091,52888,60145,2037816,"2020-08-19T08:46:16Z"));
        countries.add(new Country("Russian Federation","RU","russia",4718,930276,129,15836,6472,741045,"2020-08-19T08:46:16Z"));
        countries.add(new Country("South Africa","ZA","south-africa",2258,592144,282,12264,7797,485468,"2020-08-19T08:46:16Z"));
        countries.add(new Country("Peru","PE","peru",5547,541493,200,26481,3302,374019,"2020-08-19T08:46:16Z"));
        countries.add(new Country("Mexico","MX","mexico",5506,531239,751,57774,2969,433809,"2020-08-19T08:46:16Z"));
        countries.add(new Country("Colombia","CO","colombia",12462,489122,247,15619,10798,312323,"2020-08-19T08:46:16Z"));
        countries.add(new Country("Chile","CL","chile",1353,388855,33,10546,2055,362440,"2020-08-19T08:46:16Z"));
        countries.add(new Country("Spain","ES","spain",5114,364196,24,28670,0,150376,"2020-08-19T08:46:16Z"));
        return countries;
    }

}