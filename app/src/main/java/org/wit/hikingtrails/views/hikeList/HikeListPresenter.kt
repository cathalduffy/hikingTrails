package org.wit.hikingtrails.views.hikeList

//import android.content.Intent
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import org.wit.hikingtrails.activities.HikeMapsActivity
//import org.wit.hikingtrails.views.hike.HikeView
//import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.models.HikeModel

import org.wit.hikingtrails.views.BasePresenter
import org.wit.hikingtrails.views.BaseView
import org.wit.hikingtrails.views.VIEW

class HikeListPresenter(view: BaseView) : BasePresenter(view) {

    fun doAddHike() {
        view?.navigateTo(VIEW.HIKE)
    }

    fun doEditHike(hike: HikeModel) {
        view?.navigateTo(VIEW.HIKE, 0, "hike_edit", hike)
    }

    fun doShowHikesMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun loadHikes() {
        view?.showHikes(app.hikes.findAll())
    }
}