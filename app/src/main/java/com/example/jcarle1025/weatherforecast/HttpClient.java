package com.example.jcarle1025.weatherforecast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpClient {
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String APIkey = "&appid=6bd764612432c508a699d95e676cf7ee";
    private static String units = "&units=imperial&cnt=";

    public JSONObject getWeatherData(String location, String numDays) {
        HttpURLConnection con = null;
        InputStream is = null;
        JSONObject jsonObject;
        units += numDays;

        try {
            String city =BASE_URL + location + APIkey + units;
            jsonObject = JSONfunctions.getJSONfromURL(city);
            return jsonObject;
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }
        return null;

    }

    public Bitmap getImage(String code) {
        InputStream in = null;
        int resCode = -1;
        HttpURLConnection httpConn = null;
        try {
            URL url = new URL(IMG_URL + code + ".png");
            URLConnection urlConn = url.openConnection();
            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }

            Bitmap bitmap = null;
            bitmap = BitmapFactory.decodeStream(in);
            return bitmap;
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Throwable t) {
            }
            try {
                httpConn.disconnect();
            } catch (Throwable t) {
            }
        }
        return null;
    }
}