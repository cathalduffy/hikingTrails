package org.wit.hikingtrails.views.hikeList

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.hikingtrails.activities.HikeMapsActivity
import org.wit.hikingtrails.views.hike.HikeView
import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.models.HikeModel

class HikeListPresenter(val view: HikeListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getHikes() = app.hikes.findAll()

    fun doAddHike() {
        val launcherIntent = Intent(view, HikeView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditHike(hike: HikeModel) {
        val launcherIntent = Intent(view, HikeView::class.java)
        launcherIntent.putExtra("hike_edit", hike)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun doShowHikesMap() {
        val launcherIntent = Intent(view, HikeMapsActivity::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { getHikes() }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}