package com.codingstuff;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.codingstuff.phoneauthkt.R;

public class ApfActivity extends AppCompatActivity {

    private String phoneNumber;
    private LocationManager locationManager;
    private LocationListener locationListener;

    // Request code for receiving results from other activities
    private static final int REQUEST_CODE_ACTIVITY1 = 1;
    private static final int REQUEST_CODE_ACTIVITY2 = 2;
    private static final int REQUEST_CODE_ACTIVITY3 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apf);

        // Retrieve username and registered number from Intent
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("REGISTERED_NUMBER");

        RadioButton optionRadioButton = findViewById(R.id.option1RadioButton);
        Button sosButton = findViewById(R.id.sosButtonapf);


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

                int selectedRadioButtonId = (R.id.option1RadioButton);
                RadioButton optionRadioButton = findViewById(selectedRadioButtonId);

                if (optionRadioButton != null && optionRadioButton.isChecked()) {
                    sendSOSMessageToActivity(message, "com.codingstuff.Main1", REQUEST_CODE_ACTIVITY1);
                    sendSOSMessageToActivity(message, "com.codingstuff.Main2", REQUEST_CODE_ACTIVITY2);
                    sendSOSMessageToActivity(message, "com.codingstuff.Main3", REQUEST_CODE_ACTIVITY3);
                } else {
                    sendSOSMessageToActivity(message, "com.codingstuff.Main1", REQUEST_CODE_ACTIVITY1);
                    sendSOSMessageToActivity(message, "com.codingstuff.Main3", REQUEST_CODE_ACTIVITY2);
                }

                Toast.makeText(this, "SOS message sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSOSMessageToActivity(String message, String activityPackage, int requestCode) {
        Intent activityIntent = new Intent(activityPackage);
        activityIntent.putExtra("SOS_MESSAGE", message);
        startActivityForResult(activityIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String responseMessage = data.getStringExtra("RESPONSE_MESSAGE");
            Toast.makeText(this, "Response from activity: " + responseMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
