package org.wit.hikingtrails.views.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import org.wit.hikingtrails.R
import kotlinx.android.synthetic.main.activity_hike_maps.*
import kotlinx.android.synthetic.main.content_hike_maps.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
//import org.wit.hikingtrails.helpers.readImageFromPath
import org.wit.hikingtrails.models.HikeModel
import readImageFromPath

class HikeMapView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: HikeMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hike_maps)
        setSupportActionBar(toolbarMaps)
        presenter = HikeMapPresenter(this)

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            GlobalScope.launch(Dispatchers.Main) {
                presenter.doPopulateMap(it)
            }
        }
    }

    fun showHike(hike: HikeModel) {
        currentName.text = hike.name
        currentDescription.text = hike.description
        imageView.setImageBitmap(readImageFromPath(this, hike.image.toString()))
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        GlobalScope.launch(Dispatchers.Main) {
            presenter.doMarkerSelected(marker)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
