package org.wit.hikingtrails.activities

import android.content.Intent
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

class HikeListActivity : AppCompatActivity(), HikeListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityHikeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHikeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = HikeAdapter(app.hikes.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, HikeActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHikeClick(hike: HikeModel) {
        val launcherIntent = Intent(this, HikeActivity::class.java)
        launcherIntent.putExtra("hike_edit", hike)
        startActivityForResult(launcherIntent,0)
    }
}