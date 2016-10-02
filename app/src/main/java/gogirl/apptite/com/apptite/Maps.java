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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.widget.TextView;
import android.widget.Toast;

public class Maps extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private HashMap<Marker, MyMarker> mHashMap;
    private HashSet<MyMarker> mset = new HashSet<MyMarker>();
    private String dialogText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mHashMap = new HashMap<Marker, MyMarker>();

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
        if (dialogText == "Safe Point") {
            mset.add(new MyMarker("Safe Point", location.getLatitude(), location.getLongitude()));
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                    .title("Safe point")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }
        else if(dialogText == "Unsafe Point") {
            mset.add(new MyMarker("Unsafe Point", location.getLatitude(), location.getLongitude()));
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                    .title("Unsafe point")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
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
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            Location location = locationManager.getLastKnownLocation(bestProvider);

            Double lat, lon;
            try {
                lat = location.getLatitude();
                lon = location.getLongitude();
                CameraUpdate center=
                        CameraUpdateFactory.newLatLng(new LatLng(lat,
                                lon));
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
                if (dialogText == "Safe Point") {
                    if(mset.contains(new MyMarker("Safe Point", lat, lon))){
                        Toast.makeText(getApplicationContext(), "Already Marked", Toast.LENGTH_LONG).show();
                    }else{
                        mset.add(new MyMarker("Safe Point", lat, lon));
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                                .title("safe point")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }
                }
                else if(dialogText == "Unsafe Point") {
                    if(mset.contains(new MyMarker("Unsafe Point", lat, lon))){
                        Toast.makeText(getApplicationContext(), "Already Marked", Toast.LENGTH_LONG).show();
                    }
                    else{
                        mset.add(new MyMarker("Unsafe Point", lat, lon));
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                                .title("safe point")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }

                }
                //Toast.makeText(getApplicationContext(), "lat" + lat + "lon" + lon, Toast.LENGTH_LONG).show();
            } catch (NullPointerException e) {
                e.printStackTrace();

            }
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.
                    checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        loadSet();
        plot(mset);
    }

    private void dialog() {
        final CharSequence[] items = {"Safe Point","Unsafe Point"};
        AlertDialog.Builder alt_builder = new AlertDialog.Builder(this);
        alt_builder.setIcon(R.drawable.icon);
        alt_builder.setTitle("Mark your current location as");
        alt_builder.setSingleChoiceItems(items, -1, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(getApplicationContext(), "Your current location marked as " + items[item], Toast.LENGTH_SHORT).show();
                dialogText = (String) items[item];
                getLocation();
                dialog.dismiss();// dismiss the alertbox choosing
            }
        });
        AlertDialog alert = alt_builder.create();
        alert.show();
    }
    public void loadSet(){
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
    }

    private void plot(Set<MyMarker> set){
        if(set.size()>0){
            for(MyMarker myMarker: set) {
                if (myMarker.getmLabel() == "Safe Point") {
                    MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                    markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    Marker currentMarker = mMap.addMarker(markerOption);
                    mHashMap.put(currentMarker, myMarker);
                    mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());

                }
                else if(myMarker.getmLabel() == "Unsafe Point"){
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