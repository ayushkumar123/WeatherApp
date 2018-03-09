package com.ayush.weatherapp;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String key = "7ff74a073b76ecdca186cb129de9fe89";
    public static final String weatherWeb = "https://api.darksky.net/forecast/";
    String ACCESS_COARSE_LOCATION;
    LocationManager locationManager;
    double longitudeNetwork, latitudeNetwork;

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LocationListener locationListenerNetwork = new LocationListener() {
            public void onLocationChanged(Location location) {
                longitudeNetwork = location.getLongitude();
                latitudeNetwork = location.getLatitude();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        class RetrieveDataTask extends AsyncTask<Object, Void, JSONObject> {
                            View view;
                            String latitude;
                            String longitude;

                            public RetrieveDataTask(String latitude, String longitude) {
                                this.latitude = latitude;
                                this.longitude = longitude;
                            }

                            protected JSONObject doInBackground(Object... objects) {
                                String urlString = weatherWeb + key + "/" + latitude + "," + longitude;
                                try {
                                    URL url = new URL(urlString);
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("GET");
                                    InputStream in = new BufferedInputStream(conn.getInputStream());
                                    String response = convertStreamToString(in);
                                    JSONObject json = new JSONObject(response);
                                    return json;
                                } catch (Exception e) {
                                    Log.e("URL_ERROR", urlString);
                                    return null;
                                }
                            }

                            @Override
                            protected void onPostExecute(JSONObject jsonObject) {
                                super.onPostExecute(jsonObject);
                                try {
                                    String temperatureMin = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(0))).getString("temperatureMin");
                                    ((TextView) view.findViewById(R.id.mintemp)).setText(temperatureMin);
                                    String temperatureMax = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(0))).getString("temperatureMax");
                                    ((TextView) view.findViewById(R.id.maxtemp)).setText(temperatureMax);
                                    String summary = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(0))).getString("summary");
                                    ((TextView) view.findViewById(R.id.description)).setText(summary);
                                    String precipProb = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(0))).getString("precipProbability");
                                    ((TextView) view.findViewById(R.id.precipprob)).setText(precipProb);
                                    String precipType = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(0))).getString("precipType");
                                    ((TextView) view.findViewById(R.id.preciptype)).setText(precipType);
                                    Date date = new Date(((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(0))).getString("precipIntensityMaxTime"));
                                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                                    String s = formatter.format(date);
                                    ((TextView) view.findViewById(R.id.preciptime)).setText(s);
                                    String icon = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(0))).getString("icon");

                                    for (int i = 1; i <= 7; i = i + 1) {
                                        String weektemperatureMin = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(i))).getString("temperatureMin");
                                        String weektemperatureMax = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(i))).getString("temperatureMax");
                                        Date time = new Date(((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(i))).getString("time"));
                                        SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd");
                                        String s2 = formatter2.format(time);
                                        String weekicon = ((JSONObject)(jsonObject.getJSONObject("daily").getJSONArray("data").get(i))).getString("icon");



                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }


                    }
                    });

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }
}
