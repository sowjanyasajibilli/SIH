package com.codingstuff

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.codingstuff.phoneauthkt.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Main3 : AppCompatActivity(), OnMapReadyCallback {

    private var mGoogleMap: GoogleMap? = null
    private val FINE_PERMISSION_CODE = 1
    private var currentlocation: Location? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main3)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                FINE_PERMISSION_CODE
            )
            return
        }
        val task = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentlocation = location
                val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
                mapFragment!!.getMapAsync(this@Main3)

                val sosMessage = intent.getStringExtra("SOS_MESSAGE")
                showSOSAlert(sosMessage)
            }
        }
    }

    private fun showSOSAlert(sosMessage: String?) {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("SOS Alert")
        alertDialogBuilder.setMessage(sosMessage)
        alertDialogBuilder.setPositiveButton("Accept",
            DialogInterface.OnClickListener { dialog, which ->
                // Get the rough time taken to arrive and send it
                val timeTaken = calculateRoughTimeTaken()
                sendAcceptResponse(timeTaken)
            })
        alertDialogBuilder.setNegativeButton("Reject",
            DialogInterface.OnClickListener { dialog, which ->
                // Send request rejected message
                sendRejectResponse()
                // Handle rejection if needed
                finish() // Close the activity if rejected
            })
        alertDialogBuilder.show()
    }

    private fun calculateRoughTimeTaken(): String {
        // Implement your logic to calculate the rough time taken
        // For example, you can use distance and average speed to estimate the time
        return "30 minutes"
    }

    private fun sendAcceptResponse(timeTaken: String) {
        // Implement code to send the response with the estimated time taken
        Toast.makeText(this, "Accepted: Rough time taken to arrive - $timeTaken", Toast.LENGTH_SHORT).show()
        // You may want to perform additional actions here, such as navigation
        navigateToLocation()
        // Send the response message to AmbulanceActivity
        sendResponse("Accepted: Rough time taken to arrive - $timeTaken")
    }

    private fun sendRejectResponse() {
        // Implement code to send the request rejected message
        Toast.makeText(this, "Request Rejected", Toast.LENGTH_SHORT).show()
        // Additional actions on rejection can be added here
        // Send the response message to AmbulanceActivity
        sendResponse("Request Rejected")
    }

    private fun navigateToLocation() {
        if (currentlocation != null && mGoogleMap != null) {
            val sydney = LatLng(currentlocation!!.latitude, currentlocation!!.longitude)
            mGoogleMap!!.addMarker(MarkerOptions().position(sydney).title("MY LOCATION"))
            mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
    }

    private fun sendResponse(responseMessage: String) {
        val resultIntent = Intent()
        resultIntent.putExtra("RESPONSE_MESSAGE", responseMessage)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                Toast.makeText(this, "Please grant location permission", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
