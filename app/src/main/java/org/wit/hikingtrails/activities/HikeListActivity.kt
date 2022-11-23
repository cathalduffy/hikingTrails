package org.wit.hikingtrails.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import org.wit.hikingtrails.R
import org.wit.hikingtrails.adapters.HikeAdapter
import org.wit.hikingtrails.adapters.HikeListener
import org.wit.hikingtrails.databinding.ActivityHikeListBinding
import org.wit.hikingtrails.main.MainApp
import org.wit.hikingtrails.models.HikeModel

class HikeListActivity : AppCompatActivity(), HikeListener/*, MultiplePermissionsListener*/ {

    lateinit var app: MainApp
    private lateinit var binding: ActivityHikeListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

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
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_map -> {
                val launcherIntent = Intent(this, HikeMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
            R.id.item_add -> {
                val launcherIntent = Intent(this, HikeActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                val launcherIntent = Intent(this, SignInActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHikeClick(hike: HikeModel) {
        val launcherIntent = Intent(this, HikeActivity::class.java)
        launcherIntent.putExtra("hike_edit", hike)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadHikes() }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
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