package Database;

import org.json.JSONObject;

//stores weather data

public class WeatherDB {
    private String latlon;
    private String JSON;

    public WeatherDB(String[] positions, JSONObject object){
        this.latlon = positions[0]+positions[1];
        this.JSON = object.toString();
    }

    public String getJSON() {
        return JSON;
    }

    public String getLatlon() {
        return latlon;
    }


}
