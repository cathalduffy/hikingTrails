package org.wit.hikingtrails.main

import android.app.Application
import org.wit.hikingtrails.models.*
import org.wit.hikingtrails.room.HikeStoreRoom
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var hikes: HikeStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        hikes = HikeFireStore(applicationContext)
//        hikes = HikeStoreRoom(applicationContext)
//        hikes = HikeJSONStore(applicationContext)
        i("Hiking Trails started")
    }
}