package org.wit.hikingtrails.views.hikeList

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.models.HikeModel

import org.wit.hikingtrails.views.BasePresenter
import org.wit.hikingtrails.views.BaseView
import org.wit.hikingtrails.views.VIEW
import org.wit.hikingtrails.views.hike.HikeView
import org.wit.hikingtrails.views.map.HikeMapView
import org.wit.hikingtrails.views.signIn.SignInView

class HikeListPresenter(view: BaseView) : BasePresenter(view) {

    var hike = HikeModel()

    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var editIntentLauncher : ActivityResultLauncher<Intent>

    init {
        registerEditCallback()
        registerRefreshCallback()
    }

    suspend fun getHikes() = app.hikes.findAll()

    suspend fun updateFav(hike: HikeModel) { app.hikes.update(hike)}


    fun doAddHike() {
        val launcherIntent = Intent(view, HikeView::class.java)
        editIntentLauncher.launch(launcherIntent)
    }

    fun doEditHike(hike: HikeModel) {
        val launcherIntent = Intent(view, HikeView::class.java)
        launcherIntent.putExtra("hike_edit", hike)
        editIntentLauncher.launch(launcherIntent)
    }

    fun doShowHikesMap() {
        val launcherIntent = Intent(view, HikeMapView::class.java)
        editIntentLauncher.launch(launcherIntent)
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        val launcherIntent = Intent(view, SignInView::class.java)
        editIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view!!.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {
                GlobalScope.launch(Dispatchers.Main){
                    getHikes()
                }
            }
    }
    private fun registerEditCallback() {
        editIntentLauncher =
            view!!.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }

    }
    
//    suspend fun loadHikes() {
//        view?.showHikes(app.hikes.findAll())
//    }
}