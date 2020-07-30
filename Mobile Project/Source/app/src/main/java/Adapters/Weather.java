package Adapters;

/**
 * Bean object for weather data.
 * Stores data from JSON call.
 * Only stores data which will be displayed
 */

public class Weather {
    private int temperature;

    private String humidity;

    private String wind;

    private String condition;

    private String weathericon;

    private String dayOfWeek;

    public Weather(String humidity,String wind, String weatherIcon, int temperature, String condition) {

        this.weathericon = weatherIcon;
        this.temperature = temperature;
        this.condition = condition;
        this.wind = wind;
        this.humidity = humidity;
    }

    public String getDayOfWeek() {

        return dayOfWeek;
    }

    public String getWeatherIcon() {

        return weathericon;
    }

    public int getTemperature() {

        return temperature;
    }

    public String getCondition() {

        return condition;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
