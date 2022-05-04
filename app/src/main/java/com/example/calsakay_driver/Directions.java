package com.example.calsakay_driver;

import static java.net.URLEncoder.encode;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Directions extends AppCompatActivity implements DatabaseAccessCallback{

    WebView wvDirections;
    String rideOrigin, rideDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        Intent intent = getIntent();
        rideOrigin = intent.getStringExtra("rideOrigin");
        rideDestination = intent.getStringExtra("rideDestination");

        DatabaseAccess getLocations = new DatabaseAccess(this);
        getLocations.executeQuery("SELECT * FROM `locations` WHERE `id` = " + rideOrigin + " OR `id` = " + rideDestination);

        wvDirections = (WebView) findViewById(R.id.wbDirections);
    }

    @Override
    public void QueryResponse(List<String[]> data) {

        String pickupPlace = data.get(0)[1], pickupPlaceId = data.get(0)[2], destinationPlace = data.get(1)[1], destinationPlaceId = data.get(1)[1];

        wvDirections.loadUrl("https://www.google.com/maps/dir/?api=1&origin=" +
                pickupPlace.replaceAll(" ", "%20") + "&origin_place_id=" +
                pickupPlaceId + "&destination=" +
                destinationPlace.replaceAll(" ", "%20") + "&destination_place_id=" +
                destinationPlaceId + "&travelmode=driving&dir_action=navigate");
        wvDirections.getSettings().setJavaScriptEnabled(true);
        wvDirections.setWebViewClient(new WebViewClient());
    }
}