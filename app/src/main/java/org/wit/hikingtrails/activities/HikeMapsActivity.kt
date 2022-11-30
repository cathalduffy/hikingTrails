//package org.wit.hikingtrails.activities
//
//import android.os.Bundle
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.Marker
//import com.google.android.gms.maps.model.MarkerOptions
//import androidx.navigation.ui.AppBarConfiguration
//import com.google.android.gms.maps.GoogleMap
//import org.wit.hikingtrails.R
//import org.wit.hikingtrails.databinding.ActivityHikeMapsBinding
//import org.wit.hikingtrails.databinding.ContentHikeMapsBinding
//import org.wit.hikingtrails.main.MainApp
//
//class HikeMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {
//
////    private lateinit var binding: ActivityHikeMapsBinding
////    private lateinit var contentBinding: ContentHikeMapsBinding
//    lateinit var map: GoogleMap
//       lateinit var app: MainApp
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        app = application as MainApp
////        binding = ActivityHikeMapsBinding.inflate(layoutInflater)
////        setContentView(binding.root)
////        setSupportActionBar(binding.toolbar)
////        contentBinding = ContentHikeMapsBinding.bind(binding.root)
////        contentBinding.mapView.onCreate(savedInstanceState)
////
////        contentBinding.mapView.getMapAsync {
////            map = it
////            configureMap()
////        }
////    }
//
//    fun configureMap() {
//        map.setOnMarkerClickListener(this)
//        map.uiSettings.setZoomControlsEnabled(true)
//        app.hikes.findAll().forEach {
//            val loc = LatLng(it.lat, it.lng)
//            val options = MarkerOptions().title(it.name).position(loc)
//            map.addMarker(options).tag = it.id
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
//        }
//    }
//
//    override fun onMarkerClick(marker: Marker): Boolean {
//        val currentTitle: TextView = findViewById(R.id.hikeName     )
//        currentTitle.text = marker.title
//        return false
//    }
//
////    override fun onDestroy() {
////        super.onDestroy()
////        contentBinding.mapView.onDestroy()
////    }
//
////    override fun onLowMemory() {
////        super.onLowMemory()
////        contentBinding.mapView.onLowMemory()
////    }
//
//    override fun onPause() {
//        super.onPause()
//        contentBinding.mapView.onPause()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        contentBinding.mapView.onResume()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        contentBinding.mapView.onSaveInstanceState(outState)
//    }
//}