package br.com.borges.lucas.gpstrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.borges.lucas.gpstrack.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest

class MainActivity : AppCompatActivity() {

  companion object{
    const val DEFAULT_UPDATE_INTERVAL : Long = 30
    const val FAST_UPDATE_INTERVAL : Long = 5
  }
  private lateinit var binding: ActivityMainBinding
  // Variable to remember if we are tracking location or not
  private var updateOn: Boolean = false

  // Location request is a config file for all settings related to FusedLocationProviderClient
  private lateinit var locationRequest: LocationRequest

  // Googles API for location services
  private lateinit var fusedLocationProviderClient : FusedLocationProviderClient

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    locationRequest = LocationRequest.create().apply {
      interval = ( 1000 * DEFAULT_UPDATE_INTERVAL )
      fastestInterval = ( 1000 * FAST_UPDATE_INTERVAL )
      priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }
  }
}