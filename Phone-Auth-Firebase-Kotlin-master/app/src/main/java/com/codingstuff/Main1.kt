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

class Main1 : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap: GoogleMap? = null
    private var currentLocation: Location? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        lastLocation
    }

    private val lastLocation: Unit
        private get() {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    FINE_PERMISSION_CODE
                )
                return
            }
            fusedLocationProviderClient!!.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    currentLocation = location
                    val mapFragment =
                        supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
                    mapFragment?.getMapAsync(this@Main1)
                    val sosMessage = intent.getStringExtra("SOS_MESSAGE")
                    showSOSAlert(sosMessage)
                }
            }
        }

    private fun showSOSAlert(sosMessage: String?) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("SOS Alert")
        alertDialogBuilder.setMessage(sosMessage)
        alertDialogBuilder.setPositiveButton("Accept") { dialog: DialogInterface?, which: Int ->
            val timeTaken = calculateRoughTimeTaken()
            sendAcceptResponse(timeTaken)
        }
        alertDialogBuilder.setNegativeButton("Reject") { dialog: DialogInterface?, which: Int ->
            sendRejectResponse()
            finish()
        }
        alertDialogBuilder.show()
    }

    private fun calculateRoughTimeTaken(): String {
        return "30 minutes"
    }

    private fun sendAcceptResponse(timeTaken: String) {
        Toast.makeText(
            this,
            "Accepted: Rough time taken to arrive - $timeTaken",
            Toast.LENGTH_SHORT
        ).show()
        navigateToLocation()
        sendResponse("Accepted: Rough time taken to arrive - $timeTaken")
    }

    private fun sendRejectResponse() {
        Toast.makeText(this, "Request Rejected", Toast.LENGTH_SHORT).show()
        sendResponse("Request Rejected")
    }

    private fun navigateToLocation() {
        if (currentLocation != null && mGoogleMap != null) {
            val sydney = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
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
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                lastLocation
            } else {
                Toast.makeText(this, "Please grant location permission", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val FINE_PERMISSION_CODE = 1
    }
}
