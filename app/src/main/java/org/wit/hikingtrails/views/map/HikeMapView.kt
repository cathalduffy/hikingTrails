package org.wit.hikingtrails.views.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import org.wit.hikingtrails.R
//import kotlinx.android.synthetic.main.activity_hike_maps.*
//import kotlinx.android.synthetic.main.content_hike_maps.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.wit.hikingtrails.databinding.ActivityHikeMapsBinding
import org.wit.hikingtrails.databinding.ContentHikeMapsBinding
import org.wit.hikingtrails.main.MainApp
//import org.wit.hikingtrails.helpers.readImageFromPath
import org.wit.hikingtrails.models.HikeModel
import readImageFromPath

class HikeMapView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityHikeMapsBinding
    private lateinit var contentBinding: ContentHikeMapsBinding
    lateinit var app: MainApp
    lateinit var presenter: HikeMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityHikeMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMaps)

        presenter = HikeMapPresenter(this)

        contentBinding = ContentHikeMapsBinding.bind(binding.root)

        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync{
            GlobalScope.launch(Dispatchers.Main) {
                presenter.doPopulateMap(it)
            }
        }
    }
    override fun onMarkerClick(marker: Marker): Boolean {
        GlobalScope.launch(Dispatchers.Main) {
            presenter.doMarkerSelected(marker)
        }
        return true
    }
    fun showHike(hike: HikeModel) {
        contentBinding.currentName.text = hike.name
        contentBinding.currentDescription.text = hike.description
        Picasso.get()
            .load(hike.image)
//            .into(contentBinding.imageView2)
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

}
