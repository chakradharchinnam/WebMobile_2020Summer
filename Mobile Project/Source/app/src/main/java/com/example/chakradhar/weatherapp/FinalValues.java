package com.example.chakradhar.weatherapp;

//api

public class FinalValues {
    //Values for API connection
    private static final String IDOFAPP = "3dc7262d11aa5ae80f7a0103cccd6b5b";
    public static final String WEATHERAPP_API =
            "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric&APPID="+ IDOFAPP;
    public static final String FORECAST_API =
            "http://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&units=metric&APPID="+ IDOFAPP;
    public static final String GET_ICON_IMAGE = "http://openweathermap.org/img/w/%s.png";

    //Values for database
    public static final String DB_PERU = "weatherData";
    public static final int DB_VERS = 1;
    public static final String OORUID = "LocationID";
    public static final String ABLE_PERU = "Weather";
    public static final String MATTER = "WeatherData";
}
