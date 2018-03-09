package com.ayush.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 08-Mar-18.
 */

public class ListAdapter extends RecyclerView<ListAdapter.CustomViewHolder>{

        Context context;
        JSONObject jsonObject;

        ListAdapter(Context context, JSONObject jsonObject) {
            this.context = context;
            this.jsonObject = jsonObject;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);
            return new CustomViewHolder(view);

        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return 7;
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView maxtemp, mintemp, date;
            ImageView icon;

            public CustomViewHolder(View itemView) {
                super(itemView);
                maxtemp = itemView.findViewById(R.id.maxtemp);

                try {
                    String weektemperatureMin = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(day))).getString("temperatureMin");
                    String weektemperatureMax = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(i))).getString("temperatureMax");
                    Date time = new Date(((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(i))).getString("time"));
                    SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd");
                    String s2 = formatter2.format(time);
                    String weekicon = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(i))).getString("icon");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            void bind(int day) {
                try {
                    String weektemperatureMin = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(day))).getString("temperatureMin");
                    String weektemperatureMax = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(i))).getString("temperatureMax");
                    Date time = new Date(((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(i))).getString("time"));
                    SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd");
                    String s2 = formatter2.format(time);
                    String weekicon = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(i))).getString("icon");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                maxtemp.setText();
               }
        }
    }


