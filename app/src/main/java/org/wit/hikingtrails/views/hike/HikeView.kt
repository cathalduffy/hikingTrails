package org.wit.hikingtrails.views.hike

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.squareup.picasso.Picasso
//import kotlinx.android.synthetic.main.activity_hike.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.wit.hikingtrails.R
import org.wit.hikingtrails.databinding.ActivityHikeBinding
import org.wit.hikingtrails.models.HikeModel
import timber.log.Timber

class HikeView : AppCompatActivity() {

    private lateinit var binding: ActivityHikeBinding
    private lateinit var presenter: HikePresenter
    lateinit var map: GoogleMap
    var hike = HikeModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHikeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = HikePresenter(this)

        binding.progressBar.max = 1000
        binding.amountPicker.minValue = 1
        binding.amountPicker.maxValue = 1000

        binding.btnAdd.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {
                presenter.doAddOrSave(
                    binding.hikeName.text.toString(),
                    binding.description.text.toString(),
                    if(binding.difficultyLevel.checkedRadioButtonId == R.id.Intermediate)
                        "Intermediate" else "Hard",
                    binding.amountPicker.value
                )} }

        binding.btnDelete.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                presenter.doDelete(binding.hikeName.text.toString(), binding.description.text.toString(), binding.difficulty.text.toString(), binding.distance.text.length)}
        }

        binding.chooseImage.setOnClickListener {
            presenter.cacheHike(binding.hikeName.text.toString(), binding.description.text.toString(), binding.difficulty.text.toString(), binding.distance.text.length)
            presenter.doSelectImage()
        }

        binding.hikeLocation.setOnClickListener {
            presenter.cacheHike(binding.hikeName.text.toString(), binding.description.text.toString(), binding.difficulty.text.toString(), binding.distance.text.length)
            presenter.doSetLocation()
        }

//        binding.mapView.setOnClickListener {
//            presenter.cacheHike(binding.hikeName.text.toString(), binding.description.text.toString())
//            presenter.doSetLocation()
//        }
//
//        binding.mapView2.onCreate(savedInstanceState);
//        binding.mapView2.getMapAsync {
//            map = it
//            presenter.doConfigureMap(map)
//            it.setOnMapClickListener { presenter.doSetLocation() }
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hike, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                presenter.doCancel()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun showHike(hike: HikeModel) {
        if (binding.hikeName.text.isEmpty()) binding.hikeName.setText(hike.name)
        if (binding.description.text.isEmpty())  binding.description.setText(hike.description)
        binding.distance.setText("Distance - "+hike.distance+"km")
        binding.difficulty.setText("Difficulty Level - "+hike.difficultyLevel)
        Picasso.get()
            .load(hike.image)
            .into(binding.hikeImage)

        if (hike.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_hike_image)
        }
        binding.btnAdd.setText(R.string.save_hike)
//
//        binding.lat.setText("%.6f".format(hike.lat))
//        binding.lng.setText("%.6f".format(hike.lng))

    }

    fun updateImage(image: Uri){
        Timber.i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.hikeImage)
        binding.chooseImage.setText(R.string.change_hike_image)
    }

    override fun onDestroy() {
        super.onDestroy()
//        binding.mapView2.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
//        binding.mapView2.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
//        binding.mapView2.onPause()
    }

//    override fun onResume() {
//        super.onResume()
////        binding.mapView2.onResume()
//        presenter.doRestartLocationUpdates()
//    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        binding.mapView2.onSaveInstanceState(outState)
    }

}
