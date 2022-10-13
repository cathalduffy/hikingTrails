package org.wit.hikingtrails.main

import android.app.Application
import org.wit.hikingtrails.models.HikeModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val hikes = ArrayList<HikeModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")
//        hikes.add(HikeModel("One", "About one..."))
//        hikes.add(HikeModel("Two", "About two..."))
//        hikes.add(HikeModel("Three", "About three..."))
    }
}