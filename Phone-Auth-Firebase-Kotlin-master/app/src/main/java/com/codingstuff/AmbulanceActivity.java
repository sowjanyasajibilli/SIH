package com.codingstuff;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.codingstuff.phoneauthkt.R;

public class AmbulanceActivity extends AppCompatActivity {

    private String phoneNumber;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private static final int REQUEST_CODE_ALERT = 123; // Any unique request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);

        // Retrieve username and registered number from Intent
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("REGISTERED_NUMBER");

        RadioButton optionRadioButton = findViewById(R.id.option1RadioButton);
        Button sosButton = findViewById(R.id.sosButton);

        sosButton.setOnClickListener(view -> sendSOSMessage());

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // Location updates received, you can use the location here
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Handle status changes if needed
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                // Handle provider enabled if needed
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                // Handle provider disabled if needed
            }
        };
    }

    private void sendSOSMessage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation != null) {
                double latitude = lastKnownLocation.getLatitude();
                double longitude = lastKnownLocation.getLongitude();

                String mapLink = "https://www.google.com/maps?q=" + latitude + "," + longitude;

                String message = "Help me, " + phoneNumber + ". My location: " + mapLink;
                message += ". There is an emergency.";

                Intent alertIntent = new Intent(AmbulanceActivity.this, Main1.class);
                alertIntent.putExtra("SOS_MESSAGE", message);
                startActivityForResult(alertIntent, REQUEST_CODE_ALERT);
            } else {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ALERT) {
            if (resultCode == RESULT_OK && data != null) {
                String response = data.getStringExtra("RESPONSE_MESSAGE");
                showAlert("Response from Main1", response);
            }
        }
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                })
                .setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
