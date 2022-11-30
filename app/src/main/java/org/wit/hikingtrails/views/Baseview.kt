package org.wit.hikingtrails.views

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.wit.hikingtrails.models.HikeModel
import org.wit.hikingtrails.views.location.EditLocationView
import org.wit.hikingtrails.views.map.HikeMapView
import org.wit.hikingtrails.views.hike.HikeView
import org.wit.hikingtrails.views.hikeList.HikeListView

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
    LOCATION, HIKE, MAPS, LIST
}

open abstract class BaseView() : AppCompatActivity() //AnkoLogger
{

    var basePresenter: BasePresenter? = null

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        var intent = Intent(this, HikeListView::class.java)
        when (view) {
            VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
            VIEW.HIKE -> intent = Intent(this, HikeView::class.java)
            VIEW.MAPS -> intent = Intent(this, HikeMapView::class.java)
            VIEW.LIST -> intent = Intent(this, HikeListView::class.java)
        }
        if (key != "") {
            intent.putExtra(key, value)
        }
        startActivityForResult(intent, code)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun init(toolbar: Toolbar) {
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showHike(hike: HikeModel) {}
    open fun showHikes(hikes: List<HikeModel>) {}
    open fun showProgress() {}
    open fun hideProgress() {}
}