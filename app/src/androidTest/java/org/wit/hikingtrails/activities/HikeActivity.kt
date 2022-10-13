package org.wit.hikingtrails.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.hikingtrails.databinding.ActivityHikeBinding
import org.wit.hikingtrails.models.HikeModel
import timber.log.Timber
import timber.log.Timber.i

class HikeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHikeBinding
    var hike = HikeModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHikeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Hiking Activity started..")

        binding.btnAdd.setOnClickListener() {
            hike.name= binding.hikeName.text.toString()
            if (hike.name.isNotEmpty()) {
                i("add Button Pressed: $hike.name")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a Hike Name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}