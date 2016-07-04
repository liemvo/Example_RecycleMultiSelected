package com.android.vad.recyclemultiselected;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.vad.recyclemultiselected.adapter.CountryRecyclerAdapter;
import com.android.vad.recyclemultiselected.dao.Country;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CountryRecyclerAdapter.ChangeStatusListener, View.OnClickListener{

    private ArrayList<Country> countries = null;

    RecyclerView recycleView = null;
    CountryRecyclerAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildData();
        recycleView = (RecyclerView) findViewById(R.id.recycle);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(manager);

        adapter = new CountryRecyclerAdapter(this, countries, this);
        recycleView.setAdapter(adapter);

        getSupportActionBar().setDisplayShowTitleEnabled(true);

        findViewById(R.id.delete).setOnClickListener(this);

    }

    private void buildData(){
        Resources resources = getResources();
        if(countries == null && resources != null){

            String[] names = resources.getStringArray(R.array.countries_name);
            String[] areas = resources.getStringArray(R.array.countries_area);
            String[] capitals = resources.getStringArray(R.array.countries_capital);
            TypedArray img = resources.obtainTypedArray(R.array.countries_flags);

            countries = new ArrayList<>();
            for(int i = 0;i < names.length;i++){
                Country country = new Country();
                country.setName(names[i]);
                country.setArea(areas[i]);
                country.setCapital(capitals[i]);
                country.setImage(img.getResourceId(i, R.mipmap.ic_launcher));
                country.setSelect(false);
                countries.add(country);
            }
        }

    }

    @Override
    public void onItemChangeListener(int postion, Country country) {
        try {
           countries.set(postion, country);
        } catch (Exception e){

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.delete:
                updateListCountries();
                break;
        }
    }

    private void updateListCountries(){
        ArrayList<Country> tmpCountries = new ArrayList<>();

        try {
            for(int i = 0;i<countries.size();i++){
                if(!countries.get(i).isSelect()){
                    tmpCountries.add(countries.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        countries = tmpCountries;

        adapter.setCountries(countries);
        adapter.notifyDataSetChanged();
    }
}