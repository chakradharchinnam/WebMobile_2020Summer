package com.example.chakradhar.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Adapters.Weather;
import Database.Storage;
import Database.WeatherDB;
import android.location.Location;
import android.location.LocationManager;

import java.util.List;
//gets location from user input

public class AddLocationActivity extends AppCompatActivity {
    private Button b1;
    private Button b2;

    private EditText elText;
    private String ooruPeru = null;
    private boolean kalisaka;
    Storage databaseClass;
    LocationManager locMan;
    String lats, longs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove StrictMode in order to fetch Weather APi
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        databaseClass = new Storage(this);
        setContentView(R.layout.first_page);


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            this.kalisaka = true;
        }
        else
            this.kalisaka = false;


        elText = findViewById(R.id.editText);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button5);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GPS coordinates

                if (elText.getText() == null) {
                    Toast.makeText(AddLocationActivity.this, "Please enter valid location data",
                            Toast.LENGTH_LONG).show();
                }else {
                    //ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                    locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE) ;
                    if(locMan.isProviderEnabled(LocationManager.GPS_PROVIDER))
                        getLocation();

                    if(kalisaka) {

                        try {
                            String[] position = new String[] {lats.toString(), longs.toString()};

                            createWeatherInfo(position);

                            JSONObject oneDayData = FetchData.getJSON(getApplicationContext(), FinalValues.WEATHERAPP_API, position);
                            Weather todayWeather = getOneDayWeatherData(oneDayData);
                            String temp = Integer.toString(todayWeather.getTemperature())+" C";
                            String wind = todayWeather.getWind();
                            String humidity = todayWeather.getHumidity();

                            JSONObject jsonObj = new JSONObject();
                            jsonObj.put("City", ooruPeru);
                            jsonObj.put("Temperature", temp);
                            jsonObj.put("Wind", wind);
                            jsonObj.put("Humidity", humidity);

                            WeatherDB weatherdb = new WeatherDB(position, jsonObj);
                            databaseClass.InsertWeather(weatherdb);

                        } catch (LocationIsNotAvailable| JSONException e) {
                            Toast.makeText(AddLocationActivity.this, e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //GPS coordinates

                    if (elText.getText() == null) {
                        Toast.makeText(AddLocationActivity.this, "Please enter valid location data",
                                Toast.LENGTH_LONG).show();
                    }else {
                        ooruPeru = elText.getText().toString();
                        if(kalisaka) {

                            try {
                                String[] position = FetchData.getLatLong(AddLocationActivity.this, ooruPeru);

                                createWeatherInfo(position);

                                JSONObject oneDayData = FetchData.getJSON(getApplicationContext(), FinalValues.WEATHERAPP_API, position);
                                Weather todayWeather = getOneDayWeatherData(oneDayData);
                                String temp = Integer.toString(todayWeather.getTemperature())+" C";
                                String wind = todayWeather.getWind();
                                String humidity = todayWeather.getHumidity();

                                JSONObject jsonObj = new JSONObject();
                                jsonObj.put("City", ooruPeru);
                                jsonObj.put("Temperature", temp);
                                jsonObj.put("Wind", wind);
                                jsonObj.put("Humidity", humidity);

                                WeatherDB weatherdb = new WeatherDB(position, jsonObj);
                                databaseClass.InsertWeather(weatherdb);

                            } catch (LocationIsNotAvailable| JSONException e) {
                                Toast.makeText(AddLocationActivity.this, e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }

                }

        });

    }

    private void getLocation(){
        if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            List<String> providers = locMan.getProviders(true);
            Location locationGPS = null;
            for (String provider : providers)
            {
                Location l = locMan.getLastKnownLocation(provider);
                if(l == null){
                    continue;
                }

                if(locationGPS==null || l.getAccuracy() < locationGPS.getAccuracy()){
                    locationGPS = l;
                }
            }

            if(locationGPS != null)
            {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                lats = String.valueOf(lat);
                longs = String.valueOf(longi);
            }
        }
}

    private void createWeatherInfo(String[] position) throws LocationIsNotAvailable {

        Intent weatherInfo = new Intent(AddLocationActivity.this,WeatherINFO.class);
        weatherInfo.putExtra("Position",position);
        AddLocationActivity.this.startActivity(weatherInfo);
    }


    private Weather getOneDayWeatherData(JSONObject weather){
        try{
            if(ooruPeru == null){
                this.ooruPeru = weather.getString("name");
            }
            JSONObject cond = weather.getJSONArray("weather").getJSONObject(0);
            String condition = cond.getString("description");
            System.out.println(condition);
            String icon = cond.getString("icon");
            System.out.println(icon);
            JSONObject main = weather.getJSONObject("main");

            int temperature = (int) Double.parseDouble(main.getString("temp"));
            System.out.println(temperature);
            String humidity = main.getString("humidity")+ " %";
            System.out.println(humidity);

            JSONObject wind = weather.getJSONObject("wind");
            String winddata = wind.getString("speed") + " km/h";

            return new Weather(humidity,winddata,icon,temperature,condition);

        }catch (JSONException e){
            Toast.makeText(this,"JSON error",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }

}



