package br.com.borges.lucas.gpstrack

import android.app.Application
import android.location.Location

class MyApplication : Application() {

  companion object {
    private lateinit var singleton : MyApplication
  }

  private lateinit var myLocations : List<Location>

  fun getInstance() : MyApplication {
    return singleton
  }

  fun onCreate() {
    super.onCreate()
    singleton = this
    myLocations = listOf()
  }
}