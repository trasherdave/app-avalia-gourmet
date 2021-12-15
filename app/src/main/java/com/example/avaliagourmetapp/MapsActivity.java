package com.example.avaliagourmetapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;

import static com.example.avaliagourmetapp.MainActivity.arrayLocalizacoes;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    Button buttonLocal;
    private GoogleMap map;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        buttonLocal = findViewById(R.id.buttonLocal);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }//onCreate

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //Pega a localização atual quando clicar no mapa
    @Override
    public void onMyLocationClick(@NonNull Location location) {

        View view = findViewById(R.id.layout);
        Snackbar.make(view, "Localização atual: lat: " + location.getLatitude() + " lgn: " + location.getLongitude(), Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.parseColor("#2b961f"))
                .setAction("Action", null).show();

    }//onMyLocationClick

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationClickListener(this);
        enableMyLocation();

        //setando a localizacao
        if (!arrayLocalizacoes.isEmpty()) {

            LatLng loc = getLocalizacaoPeloEndereco(this, "" + arrayLocalizacoes.get(0));

            LatLng local = new LatLng(loc.latitude, loc.longitude);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 3));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(local)
                    .zoom(10)
                    .build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

    }//onMapReady

    public LatLng getLocalizacaoPeloEndereco(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }//getLocalizacaoPeloEndereco

    public String getLocalizacaoPelaLatLng(Context context, double lat, double lng) {

        //A geocodificação reversa é o processo de conversão de coordenadas geográficas em um endereço legível por humanos.
        Geocoder coder = new Geocoder(context);
        List<Address> endereco;
        String cidade = "";
        String estado = "";

        try {

            endereco = coder.getFromLocation(lat, lng, 5);
            if (endereco == null) {
                return null;
            }

            Address location = endereco.get(0);
            cidade = location.getLocality();
            estado = location.getSubAdminArea();

        } catch (IOException ex) {

            ex.printStackTrace();
        }//catch

        return cidade + "-" + estado;

    }//getLocalizacaoPelaLatLng


    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        // [END maps_check_location_permission]

    }//enableMyLocation

    public void localAtual(View view) {

        //pegando a lozalizacao através das coordenadas
        String localizacao = getLocalizacaoPelaLatLng(this, map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());

        //removendo localizacao da posição 0 para gravar uma nova no lugar
        if (!arrayLocalizacoes.isEmpty()) {
            arrayLocalizacoes.remove(0);
        }

        //add no array
        arrayLocalizacoes.add(localizacao);
        gravarLocalizacao(localizacao);

        //chamando outra activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("localizacao", "localizacao");
        startActivity(intent);

    }//localAtual

    private void gravarLocalizacao(String local) {
        SharedPreferences sharedPreferences = getSharedPreferences("dados", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("local", local);
        boolean resposta = editor.commit();
        if (resposta) {
            //Toast.makeText(this, "Salvo no shared preferences", Toast.LENGTH_SHORT).show();
        } else {
            // Toast.makeText(this, "Não salvo", Toast.LENGTH_SHORT).show();
        }
    }//gravarLocalizacao


}//class