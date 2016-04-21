package com.example.jcarle1025.weatherforecast;

import java.util.ArrayList;

/**
 * Created by jcarle1025 on 4/10/16.
 */
public class Weather {

    public City city = new City();

    public ArrayList<Day> dayList = new ArrayList<Day>();

    public void addDay(Day d){
        dayList.add(d);
    }

    public class City {
        private String name;
        private int id;
        private float longitude;
        private float latitude;
        private String country;
        private int population;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public float getLongitude() {
            return longitude;
        }

        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }

        public float getLatitude() {
            return latitude;
        }

        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }
    }

}