package br.com.borges.lucas.gpstrack

import android.location.Location
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import br.com.borges.lucas.gpstrack.databinding.ActivityShowSavedLocationsListBinding

class ShowSavedLocationsListActivity : AppCompatActivity() {

  private lateinit var binding: ActivityShowSavedLocationsListBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityShowSavedLocationsListBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val myApplication: MyApplication = applicationContext as MyApplication
    val savedLocations: MutableList<Location> = myApplication.myLocations

    binding.lvWayPoints.adapter =
      ArrayAdapter<Location>(this, android.R.layout.simple_list_item_1, savedLocations)
  }
}