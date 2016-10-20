package gogirl.apptite.com.apptite;

import android.Manifest;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import android.os.Handler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import  gogirl.apptite.com.apptite.Connectivity.InternetConnectivity;

public class Maps extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private HashMap<Marker, MyMarker> mHashMap;
    private HashSet<MyMarker> mset = new HashSet<MyMarker>();
    private String dialogText = "";
    boolean isDuplicate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        if(new InternetConnectivity().checkConnectivity(Maps.this)) {
            mapFragment.getMapAsync(this);
        }
        else {
            Toast.makeText(Maps.this,"Can't connect to internet",Toast.LENGTH_LONG).show();
        }

        mHashMap = new HashMap<Marker, MyMarker>();
        loadSet();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
                getLocation();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

        Double lat = BigDecimal.valueOf(location.getLatitude()).setScale(3, RoundingMode.HALF_UP).doubleValue();
        Double lon = BigDecimal.valueOf(location.getLongitude()).setScale(3, RoundingMode.HALF_UP).doubleValue();

        if (dialogText.equals("Safe Point")) {
            MyMarker myMarker = new MyMarker("Safe Point", lat, lon);
            addMark(myMarker);
        }
        else if(dialogText.equals("Unsafe Point")) {
            MyMarker myMarker = new MyMarker("Unsafe Point", lat, lon);
            addMark(myMarker);
        }
    }

    public void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String bestProvider = locationManager.getBestProvider(criteria, false);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }
        if (lastKnownLocationGPS != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            Location location = locationManager.getLastKnownLocation(bestProvider);

            Double lat, lon;
            try {
                lat = location.getLatitude();
                lon = location.getLongitude();

                CameraUpdate center =
                        CameraUpdateFactory.newLatLng(new LatLng(lat,
                                lon));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);

                if (dialogText.equals("Safe Point")) {
                    MyMarker myMarker = new MyMarker("Safe Point", lat, lon);
                    addMark(myMarker);
                }
                else if (dialogText.equals("Unsafe Point")) {
                    MyMarker myMarker = new MyMarker("Unsafe Point", lat, lon);
                    addMark(myMarker);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.
                    checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }
    private void addMark(MyMarker myMarker){

        if(myMarker.getmLabel().equals("Safe Point")){
            sendToDatabase("Safe Point", myMarker.getmLatitude(), myMarker.getmLongitude());
            if(!isDuplicate){
                mset.add(myMarker);
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                Marker currentMarker = mMap.addMarker(markerOption);
                mHashMap.put(currentMarker, myMarker);
                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }

        }else if(myMarker.getmLabel().equals("Unsafe Point")){
            sendToDatabase("Unsafe Point", myMarker.getmLatitude(), myMarker.getmLongitude());
            if(!isDuplicate){
                mset.add(myMarker);
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                Marker currentMarker = mMap.addMarker(markerOption);
                mHashMap.put(currentMarker, myMarker);
                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }
        }
    }
    private void sendToDatabase(final String label, Double latitude, Double longitude) {

        class PlaceMarker extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... params) {
                try {
                    URL url = new URL("http://virtusa.azurewebsites.net/mapsdatasend.php");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    List<NameValuePair> param = new ArrayList<NameValuePair>();

                    param.add(new BasicNameValuePair("stat", params[0]));
                    param.add(new BasicNameValuePair("latitude", params[1]));
                    param.add(new BasicNameValuePair("longitude", params[2]));
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getQuery(param));
                    writer.flush();
                    writer.close();
                    os.close();

                    conn.connect();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    Log.v("Output########",sb.toString());

                    switch (sb.toString())
                    {
                        case "101":
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Maps.this, "No DB connection", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case "102":
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Maps.this, "No Parameters", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case "103":
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    isDuplicate = true;
                                    Toast.makeText(Maps.this, "Location already marked!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case "104":
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Maps.this,"Success!Your location marked as "+label,Toast.LENGTH_SHORT).show();
                                }
                            });
                    }

                } catch (Exception ex) {
                    Log.v("Exception", ex.toString());
                }
                return null;
            }

            private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
                StringBuilder result = new StringBuilder();
                boolean first = true;

                for (NameValuePair pair : params) {
                    if (first)
                        first = false;
                    else
                        result.append("&");

                    result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
                }

                return result.toString();
            }
        }
        PlaceMarker sd = new PlaceMarker();
        sd.execute(label, latitude.toString(), longitude.toString());
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private void dialog() {
        final CharSequence[] items = {"Safe Point","Unsafe Point"};
        AlertDialog.Builder alt_builder = new AlertDialog.Builder(this);
        alt_builder.setTitle("Mark your current location as");
        alt_builder.setSingleChoiceItems(items, -1, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialogText = (String) items[item];
                getLocation();
                dialog.dismiss();
            }
        });
        AlertDialog alert = alt_builder.create();
        alert.show();
    }
    public void loadSet() {
        /*
        mset.add(new MyMarker("Safe Point", Double.parseDouble("-28.5971788"), Double.parseDouble("-52.7309824")));
        mset.add(new MyMarker("Safe Point", Double.parseDouble("19.075984"), Double.parseDouble("72.877656")));
        mset.add(new MyMarker("Safe Point", Double.parseDouble("33.7266622"), Double.parseDouble("-87.1469829")));
        mset.add(new MyMarker("Safe Point", Double.parseDouble("17.399838"), Double.parseDouble("78.018554")));
        mset.add(new MyMarker("Safe Point", Double.parseDouble("17.399095"), Double.parseDouble("78.477326")));
        mset.add(new MyMarker("Safe Point", Double.parseDouble("17.419655"), Double.parseDouble("78.499000")));
        mset.add(new MyMarker("Safe Point", Double.parseDouble("33.7266622"), Double.parseDouble("-87.1469829")));
        mset.add(new MyMarker("Safe Point", Double.parseDouble("16.399838"), Double.parseDouble("78.018554")));
        mset.add(new MyMarker("Safe Point", Double.parseDouble("18.399095"), Double.parseDouble("78.477326")));
        mset.add(new MyMarker("Safe Point", Double.parseDouble("17.419655"), Double.parseDouble("-78.499000")));
        mset.add(new MyMarker("Safe Point", Double.parseDouble("-33.924869"), Double.parseDouble("18.424055")));
        mset.add(new MyMarker("Safe Point", Double.parseDouble("41.902783"), Double.parseDouble("12.496366")));
        mset.add(new MyMarker("Safe Point", Double.parseDouble("6.788071"), Double.parseDouble("79.891281")));
        mset.add(new MyMarker("Unsafe Point", Double.parseDouble("28.535516"), Double.parseDouble("77.391026")));
        mset.add(new MyMarker("Unsafe Point", Double.parseDouble("51.8917773"), Double.parseDouble("-86.0922954")));
        mset.add(new MyMarker("Unsafe Point", Double.parseDouble("17.361564"), Double.parseDouble("78.474665")));
        mset.add(new MyMarker("Unsafe Point", Double.parseDouble("51.8917773"), Double.parseDouble("-86.0922954")));
        mset.add(new MyMarker("Unsafe Point", Double.parseDouble("52.4435047"), Double.parseDouble("-3.4199249")));
        mset.add(new MyMarker("Unsafe Point", Double.parseDouble("15.361564"), Double.parseDouble("70.474665")));
        mset.add(new MyMarker("Unsafe Point", Double.parseDouble("12.862807"), Double.parseDouble("30.217636")));
        mset.add(new MyMarker("Unsafe Point", Double.parseDouble("43.4435047"), Double.parseDouble("-3.4199249")));
        mset.add(new MyMarker("Unsafe Point", Double.parseDouble("53.904540"), Double.parseDouble("27.561524")));
        */

        class RetrieveMarker extends AsyncTask<String,Void,StringBuilder> {

            @Override
            protected StringBuilder doInBackground(String... params) {
                StringBuilder sb = null;
                try
                {
                    URL url = new URL("http://virtusa.azurewebsites.net/mapsdataretrieve.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.connect();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"), 8);
                    sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                }
                catch (Exception ex)
                {
                    Log.v("Exception", ex.toString());
                }
                return sb;
            }

            @Override
            protected void onPostExecute(StringBuilder sb) {

                try {
                    JSONObject  jsonRootObject = new JSONObject(sb.toString());
                    JSONArray jsonArray = jsonRootObject.optJSONArray("markers");
                    for(int i=0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String stat = jsonObject.optString("stat");
                        Double latitude = Double.parseDouble(jsonObject.optString("latitude"));
                        Double longitude = Double.parseDouble(jsonObject.optString("longitude"));

                        mset.add(new MyMarker(stat,latitude,longitude));
                    }
                    plot(mset);
                }
                catch (Exception ex) {

                }

            }
        }
        RetrieveMarker sd = new RetrieveMarker();
        sd.execute();
    }

    private void plot(Set<MyMarker> set){
        if(set.size()>0){
            for(MyMarker myMarker: set) {

                if (myMarker.getmLabel().equals("Safe Point")) {
                    MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                    markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    Marker currentMarker = mMap.addMarker(markerOption);
                    mHashMap.put(currentMarker, myMarker);
                    mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());

                }
                else if(myMarker.getmLabel().equals("Unsafe Point")){
                    MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                    markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    Marker currentMarker = mMap.addMarker(markerOption);
                    mHashMap.put(currentMarker, myMarker);
                    mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
                }

            }
        }
    }
    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }
        @Override
        public View getInfoContents(Marker marker) {
            View v  = getLayoutInflater().inflate(R.layout.infowindow_layout, null);
            MyMarker myMarker = mHashMap.get(marker);
            TextView markerLabel = (TextView)v.findViewById(R.id.marker_label);
            markerLabel.setText(myMarker.getmLabel());
            return v;
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
