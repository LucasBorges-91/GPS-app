package br.com.borges.lucas.gpstrack

import android.app.Application
import android.location.Location

class MyApplication : Application() {

  companion object {
    private lateinit var singleton : MyApplication
  }

  lateinit var myLocations : MutableList<Location>

  fun getInstance() : MyApplication {
    return singleton
  }

  override fun onCreate() {
    super.onCreate()
    singleton = this
    myLocations = mutableListOf()
  }
}