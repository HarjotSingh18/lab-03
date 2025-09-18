package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener{

    static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    public void editCity(City oldCity, City newCity) {
        int index = dataList.indexOf(oldCity);
        dataList.set(index, newCity);
        cityAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });

        cityList.setOnItemClickListener((parent, view, position, id) -> {

            City clickedCity = dataList.get(position);
            Log.i(TAG, "Clicked: " + clickedCity.getName() + " (" + clickedCity.getProvince() + ")");

            AddCityFragment fragment = AddCityFragment.newInstance(clickedCity);
            fragment.show(getSupportFragmentManager(), "Add/Edit City");


        });

    }
}