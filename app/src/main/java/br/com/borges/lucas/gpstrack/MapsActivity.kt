package br.com.borges.lucas.gpstrack

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import br.com.borges.lucas.gpstrack.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

  private lateinit var mMap: GoogleMap
  private lateinit var binding: ActivityMapsBinding

  private var savedLocations: MutableList<Location> = mutableListOf()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMapsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    val mapFragment = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)

    val myApplication: MyApplication = applicationContext as MyApplication
    savedLocations = myApplication.myLocations
  }

  /**
   * Manipulates the map once available.
   * This callback is triggered when the map is ready to be used.
   * This is where we can add markers or lines, add listeners or move the camera. In this case,
   * we just add a marker near Sydney, Australia.
   * If Google Play services is not installed on the device, the user will be prompted to install
   * it inside the SupportMapFragment. This method will only be triggered once the user has
   * installed Google Play services and returned to the app.
   */
  override fun onMapReady(googleMap: GoogleMap) {
    mMap = googleMap

    // Add a marker in Sydney and move the camera
    val sydney = LatLng(-34.0, 151.0)
    //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
    //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    var lastLocationPlaced : LatLng = sydney

    for (location in savedLocations) {
      val latLng: LatLng = LatLng(location.latitude, location.longitude)
      val markerOptions : MarkerOptions = MarkerOptions()
      markerOptions.position(latLng)
      markerOptions.title( "Lat: ${location.latitude}, Lon: ${location.longitude}")
      mMap.addMarker(markerOptions)
      lastLocationPlaced = latLng
    }

    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLocationPlaced, 12.0f))
    mMap.setOnMarkerClickListener( object : GoogleMap.OnMarkerClickListener {
      override fun onMarkerClick(marker: Marker): Boolean {

        // Lets count the number of times the pin is clicked.

        var clicks = marker.tag as? Int

        if ( clicks == null ) {
          clicks = 0
        }
        clicks += 1
        marker.tag = clicks
        Toast.makeText( applicationContext, "Marker ${marker.title} was clicked ${marker.tag} times.", Toast.LENGTH_SHORT).show()

        return false
      }
    })
  }
}