package org.wit.hikingtrails.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
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

        app = application as MainApp
        i("Hike Activity started...")
        binding.btnAdd.setOnClickListener() {
            hike.name = binding.hikeName.text.toString()
            hike.description = binding.description.text.toString()
            if (hike.name.isNotEmpty()) {
                app.hikes.add(hike.copy())
                i("add Button Pressed: ${hike}")
                for (i in app.hikes.indices)
                { i("Hike[$i]:${this.app.hikes[i]}") }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a Hike Name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}