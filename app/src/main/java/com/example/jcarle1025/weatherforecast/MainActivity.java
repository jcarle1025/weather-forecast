package com.example.jcarle1025.weatherforecast;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    ForecastAdapter myAdapter;
    TextView title;
    EditText citySearch;
    Button go;
    Spinner num;
    String[] items= {"1","2","3","4","5","6","7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        num = (Spinner) findViewById(R.id.num);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        num.setAdapter(adapter);

        title = (TextView) findViewById(R.id.titleText);
        citySearch = (EditText) findViewById(R.id.citySearch);
        go = (Button) findViewById(R.id.goButton);

        go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            WeatherScraper task = new WeatherScraper();
            String city = citySearch.getText().toString();
            city = city.replaceAll("\\s","");
            task.execute(city, items[num.getSelectedItemPosition()]);
        } catch (Exception e){
            Log.e("onclick error", e.toString());
        }
    }

    private class WeatherScraper extends AsyncTask<String, Void, Weather>{
        @Override
        protected Weather doInBackground(String... params) {
            Weather w = new Weather();
            JSONObject obj;
            obj = ((new HttpClient()).getWeatherData(params[0], params[1]));
            try{
                w = JSONParser.getWeather(obj, Integer.parseInt(params[1]));
                for(Day d : w.dayList)
                    d.iconData = ((new HttpClient().getImage(d.getIcon())));
            } catch (JSONException e){
                e.printStackTrace();
            }
            return w;
        }

        @Override
        protected void onPostExecute(Weather weather){
            super.onPostExecute(weather);
            title.setText(weather.city.getName().toUpperCase() + " FORECAST");

            myAdapter = new ForecastAdapter(MainActivity.this, weather.dayList);
            listView.setAdapter(myAdapter);
        }
    }
}