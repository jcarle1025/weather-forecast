package com.example.jcarle1025.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ForecastAdapter extends ArrayAdapter implements Serializable{

    private final Context context;
    private final ArrayList<Day> days;

    public ForecastAdapter(Context context, ArrayList<Day> days) {
        super(context, R.layout.day_info, days);
        this.context = context;
        this.days = days;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inf.inflate(R.layout.day_info, parent, false);
        final TextView dayDate = (TextView) rowView.findViewById(R.id.dayDate);
        final ImageView dayPic = (ImageView) rowView.findViewById(R.id.dayPic);
        final TextView dayInfo = (TextView) rowView.findViewById(R.id.dayInfo);

        String[] Days = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        long unixSeconds;
        Date date;
        SimpleDateFormat sdf;
        String formattedDate;
        SimpleDateFormat f = new SimpleDateFormat("MMM dd yyyy");
        f.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        Calendar cal = Calendar.getInstance();
        String info = new String();

        Day myDay = days.get(position);
        unixSeconds = myDay.getDate();
        date = new Date(unixSeconds * 1000L);
        formattedDate = f.format(date);
        cal.setTimeInMillis(unixSeconds * 1000L);
        String dayText = Days[cal.get(Calendar.DAY_OF_WEEK) - 1]+" " +formattedDate;

        dayDate.setText(dayText);

        if(myDay.iconData != null && myDay.iconData.getByteCount()>0)
            dayPic.setImageBitmap(myDay.iconData);

        info = myDay.getDescription().toUpperCase()+"\nHI: "+myDay.temp.getMax()+"\nLO: "+myDay.temp.getMin()+
                "\nWind: "+myDay.wind.getSpeed()+"mph";
        dayInfo.setText(info);

        return rowView;
    }
}