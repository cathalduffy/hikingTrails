package org.wit.hikingtrails.views.hike

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hike.*
import kotlinx.android.synthetic.main.activity_hike.view.*
import org.wit.hikingtrails.R
import org.wit.hikingtrails.models.HikeModel
import org.wit.hikingtrails.views.BaseView
import readImageFromPath

class HikeView : BaseView()
{

    lateinit var presenter: HikePresenter
    var hike = HikeModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hike)

        progressBar.max = 1000
        amountPicker.minValue = 1
        amountPicker.maxValue = 1000

        init(toolbarAdd)

        presenter = initPresenter (HikePresenter(this)) as HikePresenter

        btnAdd.setOnClickListener{
            presenter.doAddOrSave(
                hikeName.text.toString(),
                description.text.toString(),
                if(difficultyLevel.checkedRadioButtonId == R.id.Intermediate)
            "Intermediate" else "Hard",
                amountPicker.value
            ) }

        btnDelete.setOnClickListener { presenter.doAddOrSave(hikeName.text.toString(), description.text.toString(), difficulty.text.toString(), distance.text.length) }

        chooseImage.setOnClickListener {
            presenter.cacheHike(hikeName.text.toString(), description.text.toString(), difficulty.text.toString(), distance.text.length)
            presenter.doSelectImage()
        }

        hikeLocation.setOnClickListener {
            presenter.cacheHike(hikeName.text.toString(), description.text.toString(), difficulty.text.toString(), distance.text.length)
            presenter.doSetLocation()
        }

        btnDelete.setOnClickListener { presenter.doDelete() }
    }

    override fun showHike(hike: HikeModel) {
        hikeName.setText(hike.name)
        description.setText(hike.description)
        distance.setText("Distance - "+hike.distance+"km")
        difficulty.setText("Difficulty Level - "+hike.difficultyLevel)
        hikeImage.setImageBitmap(readImageFromPath(this, hike.image.toString()))
        if (hike.image != null) {
            chooseImage.setText(R.string.change_hike_image)
        }
        btnAdd.setText(R.string.save_hike)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hike, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
//            R.id.item_delete -> {
//                presenter.doDelete()
//            }
//            R.id.item_save -> {
//                if (hikeName.text.toString().isEmpty()) {
//                    i("Empty")
//                } else {
//                    presenter.doAddOrSave(hikeName.text.toString(), description.text.toString(), difficulty.text.toString(), distance.text.length)
//                }
//            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        presenter.doCancel()
    }
}