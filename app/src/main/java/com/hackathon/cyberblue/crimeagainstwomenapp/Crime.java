package com.hackathon.cyberblue.crimeagainstwomenapp;

import java.util.Date;

/**
 * Created by ARCHAN on 15-03-2018.
 */

public class Crime {
    public String name,about,state,crime_type;
    public Double lat,lon;
    String d1;
    public String location;
    Crime(String s1, String s2, String loca1, String description, String name,String d)
    {
        this.state=s1;
        this.crime_type=s2;
        this.about=description;
        this.name=name;
        this.location=loca1;
        this.d1=d;
    }
    Crime(Double lat,Double lon)
    {
        this.lat=lat;
        this.lon=lon;
    }
}
