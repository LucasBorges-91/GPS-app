package br.com.borges.lucas.gpstrack

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import br.com.borges.lucas.gpstrack.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener

class MainActivity : AppCompatActivity() {

  companion object {
    const val DEFAULT_UPDATE_INTERVAL: Long = 30
    const val FAST_UPDATE_INTERVAL: Long = 5
    const val PERMISSIONS_FINE_LOCATION: Int = 99
  }

  private lateinit var binding: ActivityMainBinding

  // Variable to remember if we are tracking location or not
  private var updateOn: Boolean = false

  // Location request is a config file for all settings related to FusedLocationProviderClient
  private lateinit var locationRequest: LocationRequest

  // Googles API for location services
  private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Set all properties of LocationRequest
    locationRequest = LocationRequest.create().apply {

      // How often does the default location check accur?
      interval = (1000 * DEFAULT_UPDATE_INTERVAL)

      // How often does the location check accur when set to the most frequent update
      fastestInterval = (1000 * FAST_UPDATE_INTERVAL)
      priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }


    binding.swGps.setOnClickListener {
      if (binding.swGps.isChecked) {

        // Most accurate - use GPS
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        binding.tvSensor.text = "Using GPS sensors"
      } else {
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        binding.tvSensor.text = "Using Towers + WIFI"
      }
    }

    updateGPS()
  } // End onCreate method

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    when (requestCode) {
      PERMISSIONS_FINE_LOCATION -> {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          updateGPS()
        } else {
          Toast.makeText(
            this,
            "This app requires permission to be granted in order to work properly",
            Toast.LENGTH_SHORT
          ).show()
        }
      }
    }
  }

  private fun updateGPS() {
    // Get permissions from the user to track GPS
    // Get the current location from the fused client
    // Update the UI - i.e set all properties in their associated text view items

    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    if (ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
      ) == PackageManager.PERMISSION_GRANTED
    ) {
      // User provided the permission
      fusedLocationProviderClient.lastLocation.addOnSuccessListener(this, object :
        OnSuccessListener<Location> {
        override fun onSuccess(location: Location?) {

          // We got permissions. Put the values of location. XXX into the UI components.
          if (location != null) {
            updateUIValues(location)
          }
          else {
            Toast.makeText(
              applicationContext,
              "NULL",
              Toast.LENGTH_SHORT
            ).show()
          }
        }

      })
    } else {

      // Permissions not granted yet.
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(
          Array<String>(1) { Manifest.permission.ACCESS_FINE_LOCATION },
          PERMISSIONS_FINE_LOCATION
        )
      }
    }
  }

  private fun updateUIValues( location: Location ) {

    // Update all of the text view objects with a new location.
    binding.tvLat.text = location.latitude.toString()
    binding.tvLon.text = location.longitude.toString()
    binding.tvAccuracy.text = location.accuracy.toString()

    if (location.hasAltitude()) {
      binding.tvAltitude.text = location.altitude.toString()
    }
    else {
      binding.tvAltitude.text = "Not available"
    }

    if (location.hasSpeed()) {
      binding.tvSpeed.text = location.speed.toString()
    }
    else {
      binding.tvSpeed.text = "Not available"
    }
  }
}