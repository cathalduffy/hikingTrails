package org.wit.hikingtrails.views.hike

import android.content.Intent
import org.wit.hikingtrails.models.HikeModel
import org.wit.hikingtrails.models.Location
import org.wit.hikingtrails.views.*
import showImagePicker

class HikePresenter(view: BaseView) : BasePresenter(view) {

    var hike = HikeModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false;

    init {
        if (view.intent.hasExtra("hike_edit")) {
            edit = true
            hike = view.intent.extras?.getParcelable<HikeModel>("hike_edit")!!
            view.showHike(hike)
        }
    }

    fun doAddOrSave(name: String, description: String, difficulty: String, distance: Int) {
        hike.name = name
        hike.description = description
        hike.difficultyLevel = difficulty
        hike.distance = distance
        if (edit) {
            app.hikes.update(hike)
        } else {
            app.hikes.create(hike)
        }
        view?.finish()
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.hikes.remove(hike)
        view?.finish()
    }

    fun doSelectImage() {
        view?.let{
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doSetLocation() {
        if (edit == false) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", defaultLocation)
        } else {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(hike.lat, hike.lng, hike.zoom))
        }
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                hike.image = data!!.data!!
                view?.showHike(hike)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")
                hike.lat = location!!.lat
                hike.lng = location.lng
                hike.zoom = location.zoom
            }
        }
    }
}