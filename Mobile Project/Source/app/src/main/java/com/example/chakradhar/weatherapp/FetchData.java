package com.example.chakradhar.weatherapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

//gets json data for weather info
public class FetchData {

    /*
    Function to return Weather data from OpenWeatherAPI
     */
    public static JSONObject getJSON(Context context,String toUrl, String[] latLong) throws LocationIsNotAvailable {
        int responseCode;
        String api = String.format(toUrl, latLong[0],latLong[1]);
        try {

            URL url = new URL(api);
            System.out.println(url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            responseCode = connection.getResponseCode();
            if(responseCode == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                StringBuffer json = new StringBuffer();
                String tmp;

                while ((tmp = r.readLine()) != null)
                    json.append(tmp).append("\n");
                r.close();
                System.out.println(json.toString());
                JSONObject data = new JSONObject(json.toString());
                return data;
            }else {
                System.out.println("True");
                return null;
            }
        } catch (Exception e) {
            throw new LocationIsNotAvailable("Something went wrong...");
        }
    }
    /*
    Function to convert city name to lat long positions of the place
    */

    public static String[] getLatLong(Context context,String city) throws LocationIsNotAvailable {

        try{
           Geocoder get = new Geocoder(context);
           List<Address> address = get.getFromLocationName(city,1);
           Address mainAddress = address.get(0);
           System.out.println(mainAddress.toString());

           Double lat = mainAddress.getLatitude();
           Double lon = mainAddress.getLongitude();
           return new String[] {lat.toString(),lon.toString()};
       }catch (Exception e){
           throw new LocationIsNotAvailable("Entered location has not been found");
       }

    }

    /*
    Function to fetch icon from OpenWeatherApi
    */
    public static boolean loadImageFromURL(String fileUrl,
                                    ImageView iv){
        try {

            URL myFileUrl = new URL (fileUrl);
            HttpURLConnection conn =
                    (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            iv.setImageBitmap(BitmapFactory.decodeStream(is));

            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }



}

