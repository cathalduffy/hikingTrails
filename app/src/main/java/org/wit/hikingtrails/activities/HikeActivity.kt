package org.wit.hikingtrails.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.hikingtrails.R
import org.wit.hikingtrails.databinding.ActivityHikeBinding
import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.models.HikeModel
import timber.log.Timber
import timber.log.Timber.i

class HikeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHikeBinding
    var hike = HikeModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHikeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        if (intent.hasExtra("hike_edit")) {
            hike = intent.extras?.getParcelable("placemark_edit")!!
            binding.hikeName.setText(hike.name)
            binding.description.setText(hike.description)
        }

        binding.btnAdd.setOnClickListener() {
            hike.name = binding.hikeName.text.toString()
            hike.description = binding.description.text.toString()
            if (hike.name.isNotEmpty()) {
                app.hikes.create(hike.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a Hike Name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hike, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}