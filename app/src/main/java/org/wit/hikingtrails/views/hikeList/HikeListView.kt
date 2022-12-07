package org.wit.hikingtrails.views.hikeList

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.hikingtrails.R
import org.wit.hikingtrails.adapters.HikeAdapter
import org.wit.hikingtrails.adapters.HikeListener
//import org.wit.hikingtrails.databinding.ActivityHikeListBinding
import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.models.HikeModel

import android.content.Intent
import android.view.*
//import kotlinx.android.synthetic.main.activity_hike_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.wit.hikingtrails.databinding.ActivityHikeListBinding
import org.wit.hikingtrails.views.BaseView
import timber.log.Timber

class HikeListView :  BaseView(), HikeListener {

    lateinit var app: MainApp
    lateinit var binding: ActivityHikeListBinding
    lateinit var presenter: HikeListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHikeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = HikeListPresenter(this)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        updateRecyclerView()
    }

//    override fun showHikes(hikes: List<HikeModel>) {
//        recyclerView.adapter = HikeAdapter(hikes, this)
//        recyclerView.adapter?.notifyDataSetChanged()
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {

        //update the view
        super.onResume()
        updateRecyclerView()
        binding.recyclerView.adapter?.notifyDataSetChanged()
        Timber.i("recyclerView onResume")

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_add -> presenter.doAddHike()
            R.id.item_map -> presenter.doShowHikesMap()
            R.id.logout -> presenter.doLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHikeClick(hike: HikeModel) {
        presenter.doEditHike(hike)
    }

    private fun updateRecyclerView(){
        GlobalScope.launch(Dispatchers.Main){
            binding.recyclerView.adapter =
                HikeAdapter(presenter.getHikes(), this@HikeListView)
        }
    }
}