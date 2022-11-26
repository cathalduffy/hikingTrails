package org.wit.hikingtrails.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.hikingtrails.R
import org.wit.hikingtrails.databinding.ActivityHikeBinding
import org.wit.hikingtrails.models.HikeModel
import timber.log.Timber.i

class HikeView : AppCompatActivity() {

    private lateinit var binding: ActivityHikeBinding
    lateinit var presenter: HikePresenter
    var hike = HikeModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHikeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        binding.progressBar.max = 1000
        binding.amountPicker.minValue = 1
        binding.amountPicker.maxValue = 1000

        presenter = HikePresenter(this)

        binding.btnAdd.setOnClickListener() {
            hike.name = binding.hikeName.text.toString()
            hike.description = binding.description.text.toString()
            hike.difficultyLevel = if(binding.difficultyLevel.checkedRadioButtonId == R.id.Intermediate)
                "Intermediate" else "Hard"
            hike.distance = binding.amountPicker.value
            if (hike.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_hike_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                presenter.doAddOrSave(hike.name, hike.description, hike.difficultyLevel, hike.distance)

            }
            i("add Button Pressed: $hike")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            presenter.doSelectImage()
        }

        binding.hikeLocation.setOnClickListener {
            presenter.doSetLocation()
        }

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
        binding.hikeName.setText(hike.name)
        binding.description.setText(hike.description)
        binding.distance.setText("Distance - "+hike.distance+"km")
        binding.difficulty.setText("Difficulty Level - "+hike.difficultyLevel)
        binding.btnAdd.setText(R.string.save_hike)
        Picasso.get()
            .load(hike.image)
            .into(binding.hikeImage)
        if (hike.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_hike_image)
        }
        binding.btnAdd.setText(R.string.save_hike)
    }

}