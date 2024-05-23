package com.codingstuff

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.codingstuff.phoneauthkt.R
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private var registeredNumber: String? = null
    private lateinit var auth: FirebaseAuth
    companion object {
        val LOCATION_PERMISSION_REQUEST_CODE: Int
            get() = 1
        val SMS_PERMISSION_REQUEST_CODE: Int
            get() = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        requestLocationPermission()
        requestSmsPermission()

        val ambulanceButton = findViewById<Button>(R.id.ambulance)
        val policeButton = findViewById<Button>(R.id.police)
        val apfButton = findViewById<Button>(R.id.apf)

        registeredNumber = intent.getStringExtra("REGISTERED_NUMBER")

        ambulanceButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AmbulanceActivity::class.java)
            intent.putExtra("phoneNumber", registeredNumber) // Assuming registeredNumber is available in your scope
            startActivity(intent)
        }

        policeButton.setOnClickListener {
            val intent = Intent(this@MainActivity, PoliceActivity::class.java)
            intent.putExtra("phoneNumber", registeredNumber) // Assuming registeredNumber is available in your scope
            startActivity(intent)
        }

        apfButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ApfActivity::class.java)
            intent.putExtra("phoneNumber", registeredNumber) // Assuming registeredNumber is available in your scope
            startActivity(intent)
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                SMS_PERMISSION_REQUEST_CODE
            )
        }
    }
}
