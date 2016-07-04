package com.android.vad.recyclemultiselected.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.vad.recyclemultiselected.R;
import com.android.vad.recyclemultiselected.dao.Country;

import java.util.ArrayList;

/**
 * Created by liemvo on 7/5/16.
 */
public class CountryRecyclerAdapter extends RecyclerView.Adapter<CountryRecyclerAdapter.ViewHolder> {

    public interface ChangeStatusListener{
        void onItemChangeListener(int postion, Country country);
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    ArrayList<Country> countries;
    private Context mContext = null;
    private LayoutInflater mLayoutInflate;
    ChangeStatusListener changeStatusListener;

    public CountryRecyclerAdapter(Context mContext,ArrayList<Country> countries) {
        this.mContext = mContext;
        this.mLayoutInflate = LayoutInflater.from(mContext);
        this.countries = countries;
    }

    public CountryRecyclerAdapter(Context mContext,ArrayList<Country> countries, ChangeStatusListener changeStatusListener) {
        this.mContext = mContext;
        this.mLayoutInflate = LayoutInflater.from(mContext);
        this.countries = countries;
        this.changeStatusListener = changeStatusListener;
    }


    @Override
    public int getItemCount() {
        if(countries != null) return countries.size();
        return 0;
    }

    @Override
    public CountryRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = mLayoutInflate.inflate(R.layout.item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(final CountryRecyclerAdapter.ViewHolder viewHolder, int position) {



        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Country country = countries.get(viewHolder.position);
                if(country.isSelect()){
                    country.setSelect(false);
                } else {
                    country.setSelect(true);
                }
                countries.set(viewHolder.position, country);
                if(changeStatusListener != null){
                    changeStatusListener.onItemChangeListener(viewHolder.position, country);
                }
                notifyItemChanged(viewHolder.position);
            }
        });


        try {
            Country country = countries.get(position);
            if(country != null){
                viewHolder.name.setText(country.getName());
                viewHolder.capital.setText(country.getCapital());
                viewHolder.area.setText(country.getArea());
                viewHolder.imageView.setImageResource(country.getImage());
                viewHolder.position = position;

                if(country.isSelect()) viewHolder.view.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlueDark));
                else viewHolder.view.setBackgroundResource(R.drawable.item_selector);
            }
        } catch (Exception e){

        }
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView name;
        public TextView area;
        public TextView capital;
        public ImageView imageView;

        public View view;
        public int position;

        public ViewHolder(View v) {
            super(v);
            v.setClickable(true);
            this.view = v;
            this.name = (TextView) v.findViewById(R.id.name);
            this.area = (TextView) v.findViewById(R.id.area);
            this.capital = (TextView) v.findViewById(R.id.capital);
            this.imageView = (ImageView) v.findViewById(R.id.flag);
        }
    }

}
