package com.dicoding.picodiploma.asynctaskloader.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dicoding.picodiploma.asynctaskloader.R;
import com.dicoding.picodiploma.asynctaskloader.WeatherItems;

import java.util.ArrayList;

/**
 * Created by Emeth on 10/31/2016.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private ArrayList<WeatherItems> mData = new ArrayList<>();

    public WeatherAdapter() {
    }

    /**
     * Gunakan method ini jika semua datanya akan diganti
     *
     * @param items kumpulan data baru yang akan mengganti semua data yang sudah ada
     */
    public void setData(ArrayList<WeatherItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Gunakan method ini jika ada 1 data yang ditambahkan
     *
     * @param item data baru yang akan ditambahkan
     */
    public void addItem(final WeatherItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_items, viewGroup, false);
        return new WeatherViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int position) {
        weatherViewHolder.textViewNamaKota.setText(mData.get(position).getName());
        weatherViewHolder.textViewTemperature.setText(mData.get(position).getTemperature());
        weatherViewHolder.textViewDescription.setText(mData.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamaKota;
        TextView textViewTemperature;
        TextView textViewDescription;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNamaKota = itemView.findViewById(R.id.textKota);
            textViewTemperature = itemView.findViewById(R.id.textTemp);
            textViewDescription = itemView.findViewById(R.id.textDesc);
        }
    }
}





