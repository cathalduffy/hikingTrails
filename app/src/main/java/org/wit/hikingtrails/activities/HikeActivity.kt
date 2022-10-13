package org.wit.hikingtrails.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.hikingtrails.databinding.ActivityHikeBinding
import timber.log.Timber
import timber.log.Timber.i

class HikeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHikeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHikeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Placemark Activity started..")

        i("Placemark Activity started...")

        binding.btnAdd.setOnClickListener() {
            val hikeName= binding.hikeName.text.toString()
            if (hikeName.isNotEmpty()) {
                i("add Button Pressed: $hikeName")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a Hike Name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}