package com.example.jcarle1025.weatherforecast;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONParser {
    public static Weather getWeather(JSONObject jObj, int numDays) throws JSONException{
        Weather w = new Weather();

        JSONObject cityObj = getObject("city",jObj);
        w.city.setName(getString("name",cityObj));
        w.city.setId(getInt("id", cityObj));
        JSONObject coordObj = getObject("coord",cityObj);
        w.city.setLongitude(getFloat("lon", coordObj));
        w.city.setLatitude(getFloat("lat", coordObj));
        w.city.setCountry(getString("country", cityObj));
        w.city.setPopulation(getInt("population", cityObj));

        JSONArray listArray = jObj.getJSONArray("list");

        for(int i=0; i<numDays; i++){
            Day d = new Day();
            JSONObject dayObj = listArray.getJSONObject(i);
            d.setDate(getInt("dt", dayObj));
            JSONObject tempObj = getObject("temp",dayObj);//CHECK THIS SYNTAX
            d.temp.setDay(getFloat("day", tempObj));
            d.temp.setMin(getFloat("min", tempObj));
            d.temp.setMax(getFloat("max", tempObj));
            d.temp.setNight(getFloat("night", tempObj));
            d.temp.setEve(getFloat("eve", tempObj));
            d.temp.setMorn(getFloat("morn", tempObj));
            d.setPressure(getFloat("pressure", dayObj));
            d.setHumidity(getInt("humidity", dayObj));
            JSONArray weatherArray = dayObj.getJSONArray("weather");
            JSONObject wObj = weatherArray.getJSONObject(0);
            d.setConditionId(getInt("id", wObj));
            d.setMain(getString("main", wObj));
            d.setDescription(getString("description", wObj));
            d.setIcon(getString("icon",wObj));//BITMAP
            try {
                d.wind.setSpeed(getFloat("speed", dayObj));
                d.wind.setDeg(getFloat("deg", dayObj));
            }catch (Exception e){
                Log.e("no wind", e.toString());
            }

            try{
                d.clouds.setPerc(getInt("clouds", dayObj));
            } catch (Exception e){
                Log.e("no clouds", e.toString());
            }

            try {
                d.rain.setAmount(getFloat("rain", dayObj));
            } catch(Exception e){
                Log.e("no rain", e.toString());
            }
            try {
                d.snow.setAmount(getFloat("snow",dayObj));
            } catch(Exception e){
                Log.e("no snow", e.toString());
            }
            w.addDay(d);
        }
        return w;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}