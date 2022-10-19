package org.wit.hikingtrails.main

import android.app.Application
import org.wit.hikingtrails.models.HikeJSONStore
import org.wit.hikingtrails.models.HikeMemStore
import org.wit.hikingtrails.models.HikeStore
import org.wit.hikingtrails.models.HikeModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var hikes: HikeStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        hikes = HikeJSONStore(applicationContext)
        i("Hiking Trails started")
    }
}