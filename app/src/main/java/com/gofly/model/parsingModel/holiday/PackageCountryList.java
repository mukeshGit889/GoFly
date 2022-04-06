package com.gofly.model.parsingModel.holiday;

public class PackageCountryList
{
    String country_name;
    String package_country;

    public PackageCountryList(String country_name, String package_country) {
        this.country_name = country_name;
        this.package_country = package_country;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getPackage_country() {
        return package_country;
    }

    public void setPackage_country(String package_country) {
        this.package_country = package_country;
    }
}


