package org.wit.hikingtrails.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHikeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadHikes()

        registerRefreshCallback()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadHikes() }
    }

    private fun loadHikes() {
        showHikes(app.hikes.findAll())
    }

    fun showHikes (hikes: List<HikeModel>) {
        binding.recyclerView.adapter = HikeAdapter(hikes, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}