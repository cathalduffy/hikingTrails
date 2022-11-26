package org.wit.hikingtrails.activities

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import com.squareup.picasso.Picasso
import org.wit.hikingtrails.R
import org.wit.hikingtrails.activities.MapActivity
import org.wit.hikingtrails.databinding.ActivityHikeBinding
import org.wit.hikingtrails.helpers.showImagePicker
import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.models.HikeModel
import org.wit.hikingtrails.models.Location
import timber.log.Timber
import timber.log.Timber.i

class HikePresenter(private val view: HikeView) {

    var hike = HikeModel()
    lateinit var app: MainApp
    lateinit var binding: ActivityHikeBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false;

    init {
        binding = ActivityHikeBinding.inflate(view.layoutInflater)
        app = view.application as MainApp
        if (view.intent.hasExtra("hike_edit")) {
            edit = true
            hike = view.intent.extras?.getParcelable("hike_edit")!!
            view.showHike(hike)
        }
        registerImagePickerCallback()
        registerMapCallback()
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
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

//    fun doDelete() {
//        app.hikes.remove(hike)
//        view.finish()
//    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher)
    }

    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (hike.zoom != 0f) {
            location.lat =  hike.lat
            location.lng = hike.lng
            location.zoom = hike.zoom
        }
        val launcherIntent = Intent(view, MapActivity::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerImagePickerCallback() {

        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            hike.image = result.data!!.data!!
                            Picasso.get()
                                .load(hike.image)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(binding.hikeImage)
                            binding.chooseImage.setText(R.string.change_hike_image)
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
}