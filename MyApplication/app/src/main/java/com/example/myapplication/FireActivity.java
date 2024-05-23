package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class FireActivity extends AppCompatActivity {

    private RadioButton option1RadioButton;
    private RadioButton option2RadioButton;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire);

        RadioGroup optionRadioGroup = findViewById(R.id.optionRadioGroup);
        option1RadioButton = findViewById(R.id.option1RadioButton);
        option2RadioButton = findViewById(R.id.option2RadioButton);
        Button sosButtonf = findViewById(R.id.sosButtonf);

        option1RadioButton.setChecked(true);

        optionRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.option1RadioButton) {

            } else if (checkedId == R.id.option2RadioButton) {
            }
        });

        sosButtonf.setOnClickListener(view -> sendSOSMessage());

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
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

                String message = "Help me. My location: " + mapLink;


                SmsManager smsManager = SmsManager.getDefault();
                if(option1RadioButton.isChecked()) {
                    smsManager.sendTextMessage("+919642434880", null, message, null, null);
                    smsManager.sendTextMessage("+919392576573", null, message, null, null);
                } else if (option2RadioButton.isChecked()) {
                    smsManager.sendTextMessage("+919642434880", null, message, null, null);
                    smsManager.sendTextMessage("+919392576573", null, message, null, null);
                    smsManager.sendTextMessage("+919347853324", null, message, null, null);
                }
                Toast.makeText(this, "SOS message sent with location", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
        }
    }
}
