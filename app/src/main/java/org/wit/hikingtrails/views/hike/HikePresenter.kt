package org.wit.hikingtrails.views.hike

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
//import com.google.android.gms.location.LocationResult
//import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hikingtrails.helpers.createDefaultLocationRequest
//import org.wit.hikingtrails.helpers.checkLocationPermissions
//import org.wit.hikingtrails.helpers.createDefaultLocationRequest
import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.models.Location
import org.wit.hikingtrails.models.HikeModel
//import org.wit.hikingtrails.showImagePicker
import org.wit.hikingtrails.views.location.EditLocationView
import showImagePicker
import timber.log.Timber
import timber.log.Timber.i

class HikePresenter(private val view: HikeView) {
    private val locationRequest = createDefaultLocationRequest()
    var map: GoogleMap? = null
    var hike = HikeModel()
    var app: MainApp = view.application as MainApp
    //location service
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    var edit = false;
    private val location = Location(52.245696, -7.139102, 15f)

    init {

//        doPermissionLauncher()
        registerImagePickerCallback()
        registerMapCallback()

        if (view.intent.hasExtra("hike_edit")) {
            edit = true
            hike = view.intent.extras?.getParcelable("hike_edit")!!
            view.showHike(hike)
        }
//        else {
//
//            if (checkLocationPermissions(view)) {
//                doSetCurrentLocation()
//            }
//            hike.lat = location.lat
//            hike.lng = location.lng
//        }

    }


    suspend fun doAddOrSave(name: String, description: String, difficulty: String, distance: Int) {
        hike.name = name
        hike.description = description
        hike.difficultyLevel = difficulty
        hike.distance = distance
        if (edit) {
            app.hikes.update(hike)
        } else {
            app.hikes.create(hike)
        }
        view.finish()
    }

    fun doCancel() {
        view.finish()

    }

    suspend fun doDelete(toString: String, toString1: String, toString2: String, length: Int) {
        app.hikes.remove(hike)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher)
    }

    fun doSetLocation() {
        if (hike.zoom != 0f) {
            location.lat =  hike.lat
            location.lng = hike.lng
            location.zoom = hike.zoom
            locationUpdate(hike.lat, hike.lng)
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {

        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }

    @SuppressLint("MissingPermission")
    fun doRestartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(l.latitude, l.longitude)
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }
    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(hike.lat, hike.lng)
    }

    fun locationUpdate(lat: Double, lng: Double) {
        hike.lat = lat
        hike.lng = lng
        hike.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(hike.name).position(LatLng(hike.lat, hike.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hike.lat, hike.lng), hike.zoom))
        view.showHike(hike)
    }

    fun cacheHike (name: String, description: String, difficulty: String, distance: Int) {
        hike.name = name
        hike.description = description
        hike.difficultyLevel = difficulty
        hike.distance = distance
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            hike.image = result.data!!.data!!.toString()
                            view.updateImage(hike.image)
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }

            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            hike.lat = location.lat
                            hike.lng = location.lng
                            hike.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }

            }
    }

    private fun doPermissionLauncher() {
        i("permission check called")
        requestPermissionLauncher =
            view.registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted: Boolean ->
                if (isGranted) {
                    doSetCurrentLocation()
                } else {
                    locationUpdate(location.lat, location.lng)
                }
            }
    }
}