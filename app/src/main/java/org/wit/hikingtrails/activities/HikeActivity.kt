package org.wit.hikingtrails.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.hikingtrails.R
import org.wit.hikingtrails.databinding.ActivityHikeBinding
import org.wit.hikingtrails.helpers.showImagePicker
import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.models.HikeModel
import org.wit.hikingtrails.models.Location
import timber.log.Timber
import timber.log.Timber.i

class HikeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHikeBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var hike = HikeModel()
    lateinit var app: MainApp
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityHikeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Hike Activity started...")

        if (intent.hasExtra("hike_edit")) {
            edit = true
            hike = intent.extras?.getParcelable("hike_edit")!!
            binding.hikeName.setText(hike.name)
            binding.description.setText(hike.description)
            binding.btnAdd.setText(R.string.save_hike)
            Picasso.get()
                .load(hike.image)
                .into(binding.hikeImage)
        }

        binding.btnAdd.setOnClickListener() {
            hike.name = binding.hikeName.text.toString()
            hike.description = binding.description.text.toString()
            if (hike.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_hike_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.hikes.update(hike.copy())
                } else {
                    app.hikes.create(hike.copy())
                }
            }
            i("add Button Pressed: $hike")
            setResult(RESULT_OK)
            finish()
        }
        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        binding.hikeLocation.setOnClickListener {
            i ("Set Location Pressed")
            val location = Location(52.2847986, -7.5147149, 15f)
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hike, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            hike.image = result.data!!.data!!
                            Picasso.get()
                                .load(hike.image)
                                .into(binding.hikeImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { i("Map Loaded") }
    }
}