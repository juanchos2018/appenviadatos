package com.document.enviadatos;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    Button btn3;
    GoogleMap gMap;
    LocationManager lm;
    Location location;

    double lat,lon;
    public MapaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_mapa, container, false);
        btn3=(Button)vista.findViewById(R.id.btnenviarubcacion);
      //  String texto = getArguments().getString("textFromActivityB");

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviardatos();
            }
        });
        return vista;
    }



    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.v("Localizacion", location.getLatitude() + " y " + location.getLongitude());
            lm.removeUpdates(this);
        }
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

    private void enviardatos() {

        MainActivity.lat=String.valueOf(lat);
        MainActivity.lon=String.valueOf(lon);
        NavUtils.navigateUpFromSameTask(getActivity());

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
//LOCATION_SERVICE
        lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                gMap.clear();

                // Animating to the touched position
                gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                gMap.addMarker(markerOptions);
                lat=latLng.latitude;
                lon=latLng.longitude;
                double la=latLng.latitude;
                double lo=latLng.longitude;

              //  txtlatitud1.setText(String.valueOf(la));
                //txtlngitud1.setText(String.valueOf(lo));
            }
        });
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        lat=location.getLongitude();
        lon=location.getLatitude();

        LatLng aquitoy = new LatLng(latitude, longitude);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(aquitoy)
                .zoom(14)//esto es el zoom
                .bearing(30)//esto es la inclonacion
                .build();


        gMap.addMarker(new MarkerOptions().position(aquitoy).title("Aqui estoy wey"));
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
