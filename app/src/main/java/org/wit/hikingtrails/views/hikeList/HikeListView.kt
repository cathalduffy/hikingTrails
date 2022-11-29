package org.wit.hikingtrails.views.hikeList

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.hikingtrails.R
import org.wit.hikingtrails.adapters.HikeAdapter
import org.wit.hikingtrails.adapters.HikeListener
import org.wit.hikingtrails.databinding.ActivityHikeListBinding
import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.models.HikeModel

import android.content.Intent
import android.view.*
import kotlinx.android.synthetic.main.activity_hike_list.*
import org.wit.hikingtrails.views.BaseView

class HikeListView :  BaseView(), HikeListener {

    lateinit var presenter: HikeListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hike_list)
        setSupportActionBar(toolbar)

        presenter = initPresenter(HikeListPresenter(this)) as HikeListPresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadHikes()
    }

    override fun showHikes(hikes: List<HikeModel>) {
        recyclerView.adapter = HikeAdapter(hikes, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_add -> presenter.doAddHike()
            R.id.item_map -> presenter.doShowHikesMap()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHikeClick(hike: HikeModel) {
        presenter.doEditHike(hike)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadHikes()
        super.onActivityResult(requestCode, resultCode, data)
    }
}